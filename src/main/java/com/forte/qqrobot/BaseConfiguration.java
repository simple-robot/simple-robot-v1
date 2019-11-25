package com.forte.qqrobot;

import com.forte.config.Conf;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.exception.ConfigurationException;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.BaseLocalThreadPool;

import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;
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
@Conf("")
public class BaseConfiguration<T extends BaseConfiguration> {

    //**************************************
    //*          config params
    //**************************************

    private final T configuration;

    public BaseConfiguration() {
        this.configuration = (T) this;
    }

    /*
        干脆都换成static得了

     */

    /** 是否扫描了初始化监听器 */
//    private boolean scannedInitListener = false;

    /**
     * 服务器ip，默认为127.0.0.1
     */
    @Conf("simple.robot.conf.ip")
    private String ip = "127.0.0.1";

    /**
     * 本机QQ信息, 一般唯一
     */
    private LoginQQInfo loginQQInfo = null;

    /**
     * 本机QQ号, 一般唯一
     */
    @Conf("simple.robot.conf.localQQCode")
    private String localQQCode = "";

    /**
     * 本机QQ的昵称, 一般唯一
     */
    @Conf("simple.robot.conf.localQQNick")
    private String localQQNick = "";

    /**
     * 使用的编码格式，默认为UTF-8
     */
    @Conf("simple.robot.conf.encode")
    private String encode = "UTF-8";

    /**
     * 酷Q根路径的配置，默认为null, 路径一般不会有多个
     */
    @Conf("simple.robot.conf.cqPath")
    private String cqPath;

    /**
     * 需要进行的包扫描路径，默认为空
     */
//    private Set<String> scannerPackage = new HashSet<>();
    @Conf("simple.robot.conf.scannerPackage")
    private String[] scannerPackage = {};

    /**
     * 监听函数返回值的选择器，默认为选择第一个出现的Break监听。
     */
    @Conf(value = "simple.robot.conf.resultSelectType", setterName = "setResultSelectTypeByName", setterParameterType = String.class)
    private ResultSelectType resultSelectType = ResultSelectType.FIRST_BREAK;

    public void setResultSelectTypeByName(String name){
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

    // 如果这个不是null，则优先使用此配置
    private BaseLocalThreadPool.PoolConfig poolConfig = null;

    @Conf("simple.robot.conf.threadPool.corePoolSize")
    private Integer corePoolSize = 4;
    @Conf("simple.robot.conf.threadPool.maximumPoolSize")
    private Integer maximumPoolSize = 512;
    @Conf("simple.robot.conf.threadPool.keepAliveTime")
    private Long keepAliveTime = 5L;
    @Conf(value = "simple.robot.conf.threadPool.timeUnit", setterName = "setTimeUnitByName", setterParameterType = String.class)
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    // 这个比较特殊, 是使用一个类的包名进行实例化
    @Conf("simple.robot.conf.threadPool.workQueue")
    private String workQueueFrom = "java.util.concurrent.SynchronousQueue";
    // 当此参数为null的时候，通过workQueueFrom参数来反射获取实例
    private BlockingQueue<Runnable> workQueue = null;
    // 线程工厂更特殊，干脆不能进行代码配置了
    private ThreadFactory defaultThreadFactory = Thread::new;

    /**
     * 通过name获取枚举对象
     */
    public void setTimeUnitByName(String name){
        this.timeUnit = TimeUnit.valueOf(name);
    }

    /**
     * 获取线程池的阻塞队列
     */
    public BlockingQueue<Runnable> getWorkQueue(){
        if(this.workQueue != null){
            return this.workQueue;
        }else{
            if(this.workQueueFrom == null){
                return null;
            }else{
                try {
                    Class<?> clz = Class.forName(workQueueFrom);
                    Object instance = clz.newInstance();
                    this.workQueue = (BlockingQueue<Runnable>) instance;
                    return this.workQueue;
                } catch (Exception e) {
                    throw new ConfigurationException("无法读取包路径'"+ workQueueFrom +"'来作为'"+ BlockingQueue.class +"'实例。", e);
                }
            }
        }
    }

    /**
     * 获取线程池配置对象
     */
    public BaseLocalThreadPool.PoolConfig getPoolConfig(){
        if(this.poolConfig != null){
            return this.poolConfig;
        }else{
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
     */
    @Conf("simple.robot.conf.localServerEnable")
    private boolean localServerEnable = true;

    /**
     * 本地服务器使用的端口号，默认为8808
     */
    @Conf("simple.robot.conf.localServerPort")
    private int localServerPort = 8808;


    //**************************************
    //*             以下为方法
    //**************************************


    /**
     * 注册监听器
     *
     * @param listeners 监听器列表
     */
    @Deprecated
    public T registerListeners(Object... listeners) {
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();
        //遍历
        for (Object listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);
                if (scanSet.size() > 0) {
                    QQLog.info("加载[" + listener.getClass() + "]的监听函数成功：");
                    StringJoiner joiner = new StringJoiner("]\r\n\t>>>", "\t>>>", "");
                    scanSet.forEach(lm -> joiner.add(lm.getMethodToString()));
                    QQLog.info(joiner.toString());
                }
            } catch (Exception e) {
                QQLog.error("加载[" + listener.getClass() + "]的监听函数出现异常！", e);
            }
        }
        return configuration;
    }

    /**
     * 注册监听器
     * 2019-06-05
     *
     * @param listeners 监听器列表
     */
    @Deprecated
    public T registerListeners(Class<?>... listeners) {
        //获取扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();

        //遍历
        for (Class listener : listeners) {
            try {
                //扫描
                Set<ListenerMethod> scanSet = scanner.scanner(listener);
                if (scanSet.size() > 0) {
                    QQLog.info("加载[" + listener + "]的监听函数成功：");
                    StringJoiner joiner = new StringJoiner("]\r\n\t>>>", "\t>>>", "");
                    scanSet.forEach(lm -> joiner.add(lm.getMethodToString()));
                    QQLog.info(joiner.toString());
                }
            } catch (Exception e) {
                QQLog.error("加载[" + listener + "]的监听函数出现异常！", e);
            }
        }
        return configuration;
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


    /**
     * 注册一个自定义类型的MsgGet监听枚举
     * 尚在施工中
     */
    @Deprecated
    public void registerMsgGetType(String name, Class<? extends MsgGet> msgType) {
        // come soon
        throw new RobotRuntimeException("此方法尚在施工中。");
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

    public String getLocalQQNick() {
        return localQQNick;
    }

    public T setLocalQQNick(String localQQNick) {
        this.localQQNick = localQQNick;
        return configuration;
    }

    public String getLocalQQCode() {
        return this.localQQCode;
    }

    public T setLocalQQCode(String localQQCode) {
        this.localQQCode = localQQCode;
        return configuration;
    }

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

    public String getIp() {
        return ip;
    }

    /**
     * 配置IP，默认为本地IP <code>127.0.0.1</code> <br>
     * 一般情况下，此IP代表了酷Q端的IP。
     *
     * @param ip
     * @return
     */
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
                    int number = Integer.parseInt(s);
                    if (number < 0 || number > 255) {
                        throw new ConfigurationException("ip number can not use '" + s + "'");
                    }
                }
            }
        }

        // 其他情况的话，不管了，万一是个域名呢

        this.ip = ip;
        return configuration;
    }

    /**
     * 配置loginQQInfo信息
     */
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

    @Override
    public String toString() {
        return "BaseConfiguration{" +
                ", ip='" + ip + '\'' +
                ", localQQCode='" + localQQCode + '\'' +
                ", localQQNick='" + localQQNick + '\'' +
                ", encode='" + encode + '\'' +
                ", cqPath='" + cqPath + '\'' +
                ", scannerPackage=" + Arrays.toString(scannerPackage) +
                ", corePoolSize=" + corePoolSize +
                ", maximumPoolSize=" + maximumPoolSize +
                ", keepAliveTime=" + keepAliveTime +
                ", timeUnit=" + timeUnit +
                ", workQueueFrom='" + workQueueFrom + '\'' +
                ", workQueue=" + workQueue +
                ", defaultThreadFactory=" + defaultThreadFactory +
                ", localServerEnable=" + localServerEnable +
                ", localServerPort=" + localServerPort +
                '}';
    }
}
