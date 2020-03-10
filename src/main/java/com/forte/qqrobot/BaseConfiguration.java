package com.forte.qqrobot;

import com.forte.config.Conf;
import com.forte.lang.Language;
import com.forte.qqrobot.beans.function.ExFunction;
import com.forte.qqrobot.beans.function.PathAssembler;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotInfoImpl;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.exception.ConfigurationException;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.log.LogLevel;
import com.forte.qqrobot.system.CoreSystem;
import com.forte.qqrobot.utils.BaseLocalThreadPool;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 配置类的根类，定义包扫描方法
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/4 18:02
 * @since JDK1.8
 **/
// 从这里的话..idea配置不高亮啊........所以就先把完整的写在字段上吧
@Conf(value = "", comment = "核心中的基础配置类")
public class BaseConfiguration<T extends BaseConfiguration> implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 用于对多账号进行注册，先是只保存部分信息
     */
    private List<Map.Entry<String, BotInfo>> advanceBotInfo = new ArrayList<>();

    /**
     * 指定一个默认的Botinfo，没有人工指定的情况下，会默认设定为第一次注册的账号信息
     */
    private BotInfo defaultBotInfo;

    /*
        此类是在language初始化之前使用的，故此不使用语言化
     */

    //**************************************
    //*          config params
    //**************************************

    private final T configuration;


    public BaseConfiguration() {
        this.configuration = (T) this;
    }


    /**
     * 服务器ip，默认为127.0.0.1
     */
    @Conf(value = "core.ip", comment = "服务器的IP地址，一般代表为上报地址.1.8.0后弃用")
    private String ip = "127.0.0.1";

    /**
     * 提供一个默认的端口
     */
    private int port = 5700;

    /**
     * 弃用字段
     */
    @Deprecated
    private LoginQQInfo loginQQInfo = null;

    /**
     * 弃用字段
     */
    @Conf(value = "core.localQQCode", comment = "本机的QQ号，是否需要配置与组件相关。1.8.x后弃用属性。")
    @Deprecated
    private String localQQCode = "";

    /**
     * 弃用字段
     */
    @Conf(value = "core.localQQNick", comment = "本机的QQ昵称，是否需要配置与组件相关。1.8.x后弃用属性。")
    @Deprecated
    private String localQQNick = "";

    /**
     * 使用的编码格式，默认为UTF-8
     */
    @Conf(value = "core.encode", comment = "使用的编码格式，默认为UTF-8")
    private String encode = "UTF-8";

    /**
     * 酷Q根路径的配置，默认为null
     */
    @Conf(value = "core.cqPath", comment = "酷Q根路径的配置，默认为null。目前此属性用处不大。")
    private String cqPath;

    /**
     * 需要进行的包扫描路径，默认为空，即扫描启动器根路径
     */
    @Conf(value = "core.scannerPackage", comment = "需要进行的包扫描路径，默认为空，即扫描启动器根路径")
    private String[] scannerPackage = {};

    /**
     * 监听函数返回值的选择器，默认为选择第一个出现的Break监听。
     */
    @Conf(value = "core.resultSelectType", setterName = "setResultSelectTypeByName", setterParameterType = String.class,
            comment = "监听函数返回值的选择器，默认为选择第一个出现的Break监听。")
    private ResultSelectType resultSelectType = ResultSelectType.FIRST_BREAK;

    public void setResultSelectTypeByName(String name) {
        this.resultSelectType = ResultSelectType.valueOf(name);
    }

    //**************** 依赖相关 ****************//

    /**
     * 自定义依赖对象实例化规则，假如同时使用了spring之类的框架，需要对此进行配置
     */
    private DependGetter dependGetter = null;


    //**************** 线程工厂配置 ****************//

    //  ///
    //※ 各个配置的详细说明查看com.forte.qqrobot.utils.BaseLocalThreadPool.PoolConfig对象内的字段注释。
    //  ///

    /**
     * 如果这个不是null，则优先使用此配置
     */
    private BaseLocalThreadPool.PoolConfig poolConfig = null;

    /**
     * 核心池的大小
     * 默认为null，当为null的时候，默认使用最佳线程数量
     */
    @Conf(value = "core.threadPool.corePoolSize", comment = "核心池的大小。默认根据CPU核心数计算最佳线程数量 / 2")
    private Integer corePoolSize = null;

    /**
     * 线程池初始化的阻塞系数，用来决定最终的线程池线程数量。
     * 默认为0.2， 即认为你的每个监听器在执行的时候，有20%的时间是处于线程阻塞状态。
     *
     * @see CoreSystem#getBestPoolSize(double)
     * @see <a href='https://www.cnblogs.com/jpfss/p/11016180.html'>参考文章</a>
     */
    @Conf(value = "core.threadPool.blockingFactor", comment = "线程池初始化的阻塞系数，用来在未手动配置的情况下决定最终的线程池线程数量。")
    private Double blockingFactor = 0.0;

    /**
     * 线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
     * 默认为null，当为null的时候，默认为{@link #corePoolSize}的2倍
     */
    @Conf(value = "core.threadPool.maximumPoolSize", comment = "线程池最大线程数, 默认为corePoolSize的2倍")
    private Integer maximumPoolSize = null;
    /**
     * 表示线程没有任务执行时最多保持多久时间会终止。
     * 默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，
     * 直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，
     * 如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
     * 但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，
     * 直到线程池中的线程数为0；
     */
    @Conf(value = "core.threadPool.keepAliveTime", comment = "表示线程没有任务执行时最多保持多久时间会终止。")
    private Long keepAliveTime = 5L;

    /**
     * unit：参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性:
     * TimeUnit.DAYS;              //天
     * TimeUnit.HOURS;             //小时
     * TimeUnit.MINUTES;           //分钟
     * TimeUnit.SECONDS;           //秒
     * TimeUnit.MILLISECONDS;      //毫秒
     * TimeUnit.MICROSECONDS;      //微妙
     * TimeUnit.NANOSECONDS;       //纳秒
     */
    @Conf(value = "core.threadPool.timeUnit", setterName = "setTimeUnitByName", setterParameterType = String.class,
            comment = "参数keepAliveTime的时间单位")
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * 使用类路径进行实例化（文件配置的情况下）
     * 一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，
     * 会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：
     * ArrayBlockingQueue;
     * LinkedBlockingQueue;
     * SynchronousQueue;
     * ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。
     * 线程池的排队策略与BlockingQueue有关。
     */
    @Conf(value = "core.threadPool.workQueue", comment = "一个阻塞队列，用来存储等待执行的任务。")
    private String workQueueFrom = "java.util.concurrent.LinkedBlockingQueue";

    /**
     * 当此参数为null的时候，通过workQueueFrom参数来反射获取实例
     */
    private BlockingQueue<Runnable> workQueue = null;
    /**
     * 线程工厂
     */
    private ThreadFactory defaultThreadFactory = Thread::new;

    /**
     * 日志等级, 默认为info级别
     */
    @Conf(value = "core.logLevel", setterName = "setLogLevelByName", setterParameterType = String.class,
            comment = "日志等级, 默认为info级别")
    private LogLevel logLevel = LogLevel.INFO;

    public void setLogLevelByName(String name) {
        this.logLevel = LogLevel.valueOf(name);
    }


    /**
     * 设置日志默认语言
     */
    @Conf(value = "core.language", setterName = "setLanguageByTag", setterParameterType = String.class,
            comment = "使用的信息语言。默认为系统当前语言。")
    private Locale language = Locale.getDefault();

    public void setLanguageByTag(String tag) {
        language = Language.getLocaleByTag(tag);
    }


    @Conf(value = "core.bots", setterName = "registerBotsFormatter", setterParameterType = String.class,
            comment = "需要注册的bot列表。格式：{code}:{path},{code}:{path}...即，一组bot中，格式为code+冒号':'+path路径，多组bot信息使用英文半角逗号','分割。")
    private String registerBots = null;

    /**
     * 对配置文件的信息进行注册, 一般手动注册时候不需要使用此方法
     * @param registerBots registerBots
     */
    public void registerBotsFormatter(String registerBots){
        if(registerBots == null || (registerBots = registerBots.trim()).length() == 0){
                return;
        }
        // 根据逗号切割
        for (String botInfo : registerBots.split(",")) {
            if(botInfo.trim().length() == 0){
                throw new ConfigurationException("configuration 'core.bots' is malformed.");
            }
            int first = botInfo.indexOf(":");
            String code = botInfo.substring(0, first).trim();
            String path = botInfo.substring(first + 1).trim();
            if(path.endsWith("/")){
                path = path.substring(0, path.length()-1);
            }
            registerBot(code, path);
        }
    }


    //**************************************
    //*             function area
    //**************************************


    /**
     * 通过name获取枚举对象
     */
    public void setTimeUnitByName(String name) {
        this.timeUnit = TimeUnit.valueOf(name);
    }

    /**
     * 获取线程池的阻塞队列
     */
    public BlockingQueue<Runnable> getWorkQueue() {
        if (this.workQueue != null) {
            return this.workQueue;
        } else {
            if (this.workQueueFrom == null) {
                return null;
            } else {
                try {
                    Class<?> clz = Class.forName(workQueueFrom);
                    Object instance = clz.newInstance();
                    this.workQueue = (BlockingQueue<Runnable>) instance;
                    return this.workQueue;
                } catch (Exception e) {
                    throw new ConfigurationException("无法读取包路径'" + workQueueFrom + "'来作为'" + BlockingQueue.class + "'实例。", e);
                }
            }
        }
    }

    /**
     * 获取线程池配置对象
     */
    public BaseLocalThreadPool.PoolConfig getPoolConfig() {
        if (this.poolConfig != null) {
            return this.poolConfig;
        } else {
            // 如果为null，根据参数获取。
            // 阻塞系数
            if (this.blockingFactor == null) {
                this.blockingFactor = 0.0;
            }
            // 核心线程数量, 默认为最佳数量/2
            if (this.corePoolSize == null) {
                this.corePoolSize = CoreSystem.getBestPoolSize(this.blockingFactor) >> 1;
            }
            // 最大线程数量, 默认为corePoolSize的2倍+1。
            if (this.maximumPoolSize == null) {
                this.maximumPoolSize = (this.corePoolSize << 1) + 1;
            }

            BaseLocalThreadPool.PoolConfig config = new BaseLocalThreadPool.PoolConfig();
            config.setTimeUnit(this.timeUnit);
            config.setKeepAliveTime(this.keepAliveTime);
            config.setDefaultThreadFactory(defaultThreadFactory);
            config.setCorePoolSize(corePoolSize);
            config.setMaximumPoolSize(maximumPoolSize);
            config.setWorkQueue(getWorkQueue());

            this.poolConfig = config;
            return config;
        }
    }

    //**************** 本地服务器设置相关 >尚未实装< ****************//

    /**
     * 是否启用本地服务器，默认启动
     * 暂时无用
     */
    @Conf(value = "core.localServerEnable", comment = "尚未实装配置，可无视。> 是否启用本地服务器，默认为true")
    @Deprecated
    private boolean localServerEnable = true;

    /**
     * 本地服务器使用的端口号，默认为8808
     * 暂时无用
     */
    @Conf(value = "core.localServerPort", comment = "尚未实装配置，可无视。> 本地服务器使用的端口号，默认为8808")
    @Deprecated
    private int localServerPort = 8808;

    @Conf(value = "core.checkVersion", comment = "从maven仓库检查是否存在可用的、可直接覆盖的更新的核心版本并进行提示。检测范围是前两位版本号相同的情况下。")
    private Boolean checkVersion = true;


    @Conf(value = "core.enableServer", comment = "是否让组件启动一个(可能存在的)内置监听服务器，例如HTTP服务器或者ws服务器。默认开启")
    private Boolean enableServer = true;

    //**************************************
    //*             以下为方法
    //**************************************

    /**
     * 注册一个机器人的信息。
     *
     * @param botCode bot账号
     * @param path    上报地址，为一个完整的请求路径，例如：http://127.0.0.1:12345
     */
    public void registerBot(String botCode, String path) {
        if (botCode == null || botCode.trim().length() == 0) {
            botCode = null;
        }
        BotInfoImpl botInfo = new BotInfoImpl(botCode, path, null, null);
        if(getDefaultBotInfo() == null){
            setDefaultBotInfo(botInfo);
        }
        // 注册一个bot信息
        advanceBotInfo.add(new AbstractMap.SimpleEntry<>(botCode, botInfo));
    }

    /**
     * 注册一个机器人的信息。
     * @param botCode bot账号
     * @param ip      上报地址的ip
     * @param port    上报地址的端口
     * @param path     如果存在，上报地址的路径
     */
    public void registerBot(String botCode, String ip, int port, String path){
        registerBot(botCode, toPath(ip, port, path));
    }

    /**
     * 仅仅注册一个路径信息，需要是一个完整路径，例如一个http路径或者一个ws的连接路径。
     * 在启动后需要通过此路径来验证或者连接。
     * 一般来说直接使用这个就行了。
     *
     * @param path 上报路径
     */
    public void registerBot(String path) {
        registerBot(null, path);
    }

    /**
     * <pre> 仅注册一个路径信息，信息分为ip、端口、一个可能存在的额外路径。
     * <pre> 开发者如果需要实现对于ip、端口、额外路径的转化规则，尝试重写{@link #toPath(String, int, String)}方法。默认情况下为转化为http协议路径。
     * @param ip    ip地址
     * @param port  端口
     * @param path nullable
     */
    public void registerBot(String ip, int port, String path) {
        registerBot(null, toPath(ip, port, path));
    }

    /**
     * 注册一个机器人的信息。与普通的registerBot不同，此处注册的机器人会直接覆盖当前的默认机器人信息
     *
     * @param botCode bot账号
     * @param path    上报地址，为一个完整的请求路径
     */
    public void registerBotAsDefault(String botCode, String path) {
        if (botCode == null || botCode.trim().length() == 0) {
            botCode = null;
        }
        BotInfoImpl botInfo = new BotInfoImpl(botCode, path, null, null);
        setDefaultBotInfo(botInfo);
        // 注册一个bot信息
        advanceBotInfo.add(new AbstractMap.SimpleEntry<>(botCode, botInfo));
    }

    /**
     * 注册一个机器人的信息。
     * @param botCode bot账号
     * @param ip      上报地址的ip
     * @param port    上报地址的端口
     * @param path     如果存在，上报地址的路径
     */
    public void registerBotAsDefault(String botCode, String ip, int port, String path){
        registerBotAsDefault(botCode, toPath(ip, port, path));
    }

    /**
     * 仅仅注册一个路径信息，需要是一个完整路径，例如一个http路径或者一个ws的连接路径。
     * 在启动后需要通过此路径来验证或者连接
     *
     * @param path 上报路径
     */
    public void registerBotAsDefault(String path) {
        registerBotAsDefault(null, path);
    }

    /**
     * <pre> 仅注册一个路径信息，信息分为ip、端口、一个可能存在的额外路径。
     * <pre> 开发者如果需要实现对于ip、端口、额外路径的转化规则，尝试重写{@link #toPath(String, int, String)}方法。默认情况下为转化为http协议路径。
     * @param ip    ip地址
     * @param port  端口
     * @param path nullable
     */
    public void registerBotAsDefault(String ip, int port, String path) {
        registerBotAsDefault(null, toPath(ip, port, path));
    }

    /**
     * 获取当前记录的默认bot的信息
     * @return 默认bot信息
     */
    public BotInfo getDefaultBotInfo(){
        return defaultBotInfo;
    }

    public void setDefaultBotInfo(BotInfo botInfo){
        this.defaultBotInfo = botInfo;
    }

    /**
     * 获取预先注册的bot信息。
     */
    public Map<String, List<BotInfo>> getAdvanceBotInfo() {
        // 如果没有任何信息，注册一个127:5700的默认地址
        if(advanceBotInfo.size() == 0){
            registerBot("http://" + ip + ":" + port);
        }
        // 将数据转化为map，key为bot的账号（如果存在的话）
        // 不存在账号信息的，key将会为null，只有key为null的时候，list才可以有多个参数，其余情况下，一个key只能对应一个地址。
        Map<String, List<BotInfo>> botInfoMap = new HashMap<>(2);
        // 不注册多次相同的path
        Set<String> pathSet = new HashSet<>(2);

        for (Map.Entry<String, BotInfo> botEntry : advanceBotInfo) {
            BotInfo botInfo = botEntry.getValue();
            String code = botEntry.getKey();
            List<BotInfo> botInfos = botInfoMap.computeIfAbsent(code, k -> new ArrayList<>());
            if (code == null) {
                // 无账号配置
                if (pathSet.add(botInfo.getPath())) {
                    // 保存成功，无重复path，则记录这个botInfo
                    botInfos.add(botInfo);
                } else {
                    throw new ConfigurationException("Cannot register the same path multiple times: " + botInfo.getPath());
                }
            } else {
                // 有code
                if (botInfos.size() > 0) {
                    // 已经存在bot信息，抛出异常
                    throw new ConfigurationException("Cannot register the same code multiple times: " + code);
                } else {
                    // 验证path
                    if (pathSet.add(botInfo.getPath())) {
                        // 保存成功，无重复path，则记录这个botInfo
                        botInfos.add(botInfo);
                    } else {
                        throw new ConfigurationException("Cannot register the same path multiple times: " + botInfo.getPath());
                    }
                }
            }
        }

        // 返回最终结果
        return botInfoMap;
    }

    /**
     * 将ip、端口、后缀路径拼接为一个请求路径
     *
     * @param ip   ip地址
     * @param port 端口
     * @param path 路径
     * @return
     */
    protected String toPath(String ip, int port, String path) {
        StringBuilder sb = new StringBuilder("http://");
        sb.append(ip).append(':').append(port);
        if (path != null) {
            if(!path.startsWith("/")){
                sb.append("/");
            }
            sb.append(path);
        }
        return sb.toString();
    }

    /**
     * 根据当前的{@link #toPath(String, int, String)}获取路径拼接函数
     * @return 拼接函数
     */
    public PathAssembler getPathAssembler(){
        return this::toPath;
    }

    /**
     * 包扫描普通监听器
     *
     * @param packageName 包名
     */
    public T scannerListener(String packageName) {
        scanner(packageName);
        return configuration;
    }

    /**
     * 包扫描，现在的扫描已经不再仅限于监听器了
     */
    public T scanner(String packageName) {
        //添加包路径
//        scannerPackage.add(packageName);

        // 数组扩容增加
        String[] newPackageName = new String[packageName.length() + 1];
        newPackageName[this.scannerPackage.length] = packageName;
        System.arraycopy(this.scannerPackage, 0, newPackageName, 0, packageName.length());
        this.scannerPackage = newPackageName;

        return configuration;
    }



    //**************** getter & setter ****************//

    /**
     * 配置需要进行扫描的路径
     */
    public T setScannerPackage(String... packages) {
        if (packages != null) {
            this.scannerPackage =
                    Stream.concat(Arrays.stream(this.scannerPackage), Arrays.stream(packages)).distinct().toArray(String[]::new);
        }
        return configuration;
    }

    //**************** simple getter & setter ****************//


    /**
     * 获取需要进行扫描的包路径集合
     */
    public Set<String> getScannerPackage() {
        return Arrays.stream(this.scannerPackage).collect(Collectors.toSet());
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public String getLocalQQNick() {
        return localQQNick;
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public T setLocalQQNick(String localQQNick) {
        this.localQQNick = localQQNick;
        return configuration;
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public String getLocalQQCode() {
        return this.localQQCode;
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public T setLocalQQCode(String localQQCode) {
        this.localQQCode = localQQCode;
        return configuration;
    }

    /**
     * @see BotRuntime
     * @see com.forte.qqrobot.bot.BotManager
     */
    @Deprecated
    public LoginQQInfo getLoginQQInfo() {
        return loginQQInfo;
    }

    public String getEncode() {
        return encode;
    }

    public T setEncode(String encode) {
        this.encode = encode;
        return configuration;
    }

    public String getCqPath() {
        return cqPath;
    }

    public T setCqPath(String cqPath) {
        this.cqPath = cqPath;
        return configuration;
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public String getIp() {
        return ip;
    }

    /**
     * 不再进行单一ip地址配置
     */
    @Deprecated
    public T setIp(String ip) {
        // 验证IP，首先假如上来就是个xxx.xxx.xxx.xxx，直接报错
        if (ip.equals("xxx.xxx.xxx.xxx")) {
            throw new ConfigurationException("are you sure 'xxx.xxx.xxx.xxx' is an ip value ?");
        }

        // 当然，有可能是域名的情况，所以只有当符合\d+.\d+.\d+.\d+的时候才验证
        // ip: 0-255

        // 是否跳过。一些情况下，直接跳过检测
        // 例如ip是本地ip
        boolean breakMatch = ip.equals("127.0.0.1") || ip.equals("localhost");

        if (!breakMatch) {
            if (ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                // 是ip的格式，验证IP是不是都在0~255范围内
                // 切割并验证
                for (String s : ip.split("\\.")) {
                    try {
                        int number = Integer.parseInt(s);
                        if (number < 0 || number > 255) {
                            throw new ConfigurationException("ip number can not use '" + s + "'");
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        // 其他情况的话，不管了，万一是个域名呢

        this.ip = ip;
        return configuration;
    }

    /**
     * @see #registerBot(String, String)
     * @see #registerBot(String)
     * @see #registerBot(String, String, int, String)
     * @see #registerBot(String, int, String)
     * @see #registerBotAsDefault(String, String)
     * @see #registerBotAsDefault(String, String, int, String)
     * @see #registerBotAsDefault(String, int, String)
     */
    @Deprecated
    public T setLoginQQInfo(LoginQQInfo loginQQInfo) {
        this.loginQQInfo = loginQQInfo;
        this.localQQCode = loginQQInfo.getQQ();
        this.localQQNick = loginQQInfo.getName();
        return configuration;
    }

    /**
     * 获取依赖获取器
     */
    public DependGetter getDependGetter() {
        return dependGetter;
    }

    /**
     * 配置依赖获取器
     */
    public T setDependGetter(DependGetter dependGetter) {
        this.dependGetter = dependGetter;
        return configuration;
    }

    /**
     * 通过类的全包路径进行指定，通过反射创建实例
     */
    public T setDependGetter(String packPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.dependGetter = (DependGetter) Class.forName(packPath).newInstance();
        return configuration;
    }


    public boolean isLocalServerEnable() {
        return localServerEnable;
    }

    public T setLocalServerEnable(boolean localServerEnable) {
        this.localServerEnable = localServerEnable;
        return configuration;
    }

    public int getLocalServerPort() {
        return localServerPort;
    }

    public T setLocalServerPort(int localServerPort) {
        this.localServerPort = localServerPort;
        return configuration;
    }

    public final T getConfiguration() {
        return configuration;
    }


    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getWorkQueueFrom() {
        return workQueueFrom;
    }

    public Double getBlockingFactor() {
        return blockingFactor;
    }

    public void setBlockingFactor(Double blockingFactor) {
        this.blockingFactor = blockingFactor;
    }

    public void setWorkQueueFrom(String workQueueFrom) {
        this.workQueueFrom = workQueueFrom;
    }

    public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
    }

    public ThreadFactory getDefaultThreadFactory() {
        return defaultThreadFactory;
    }

    public void setDefaultThreadFactory(ThreadFactory defaultThreadFactory) {
        this.defaultThreadFactory = defaultThreadFactory;
    }

    public void setPoolConfig(BaseLocalThreadPool.PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public ResultSelectType getResultSelectType() {
        return resultSelectType;
    }

    public void setResultSelectType(ResultSelectType resultSelectType) {
        this.resultSelectType = resultSelectType;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Boolean getCheckVersion() {
        return checkVersion;
    }

    public void setCheckVersion(Boolean checkVersion) {
        this.checkVersion = checkVersion;
    }

    public Boolean getEnableServer() {
        return enableServer;
    }

    public void setEnableServer(Boolean enableServer) {
        this.enableServer = enableServer;
    }
}
