package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.lang.Language;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import com.forte.plusutils.consoleplus.console.colors.BackGroundColorTypes;
import com.forte.plusutils.consoleplus.console.colors.ColorTypes;
import com.forte.plusutils.consoleplus.console.colors.FontColorTypes;
import com.forte.qqrobot.anno.Config;
import com.forte.qqrobot.anno.CoreVersion;
import com.forte.qqrobot.anno.depend.AllBeans;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.exception.RobotRunException;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.MsgIntercept;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.scanner.Register;
import com.forte.qqrobot.scanner.ScannerManager;
import com.forte.qqrobot.sender.*;
import com.forte.qqrobot.sender.intercept.SenderGetIntercept;
import com.forte.qqrobot.sender.intercept.SenderSendIntercept;
import com.forte.qqrobot.sender.intercept.SenderSetIntercept;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.*;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 启动类总抽象类，在此实现部分通用功能
 * 实现closeable接口
 *
 * @param <CONFIG> 对应的插件配置类类型
 * @param <SP_API> 由组件实现方提供的特殊API对象。
 *                 此类型没有任何限制，一般情况下我希望此类型是提供于我提供的三大API接口中不存在的API。
 *                 例如：获取插件信息等等。
 *                 有时候，这个类型可能就是你实现了三大API接口的那个对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:18
 * @since JDK1.8
 **/
@CoreVersion(
        version = "",
        author = "ForteScarlet",
        email = "ForteScarlet@163.com"
)
public abstract class BaseApplication<CONFIG extends BaseConfiguration, SP_API> implements Closeable {

//    private static final String LOG_TAG_HEAD_CHAR = "run";

    protected static final QQLogLang RUN_LOG = new QQLogLang("run");

    /**
     * 获取日志对象
     */
    protected QQLogLang getLog(){
        return RUN_LOG;
    }

//    /**
//     * <pre> 启动器中使用的日志，tag会有一个前缀：run
//     * <pre> 即例如： {@code "tag1.name" -> "run.tag1.name"}
//     * @param tag tag
//     */
//    protected String runLogTag(String tag){
//        return LOG_TAG_HEAD_CHAR + '.' + tag;
//    }

    /**
     * 是否已经检测过java的版本
     */
    @Deprecated
    private static boolean JAVA_VERSION_DETECTED = false;

    /** 为Java的版本检测提供一把锁 */
    @Deprecated
    private static final byte[] JAVA_VERSION_DETECTION_LOCK = new byte[0];

    /**
     * Java版本检测，只检测一次
     * 突然发现，这么检测没什么意义
     */
    @Deprecated
    private void javaVersionDetection(){
        // 如果尚未检测，尝试获取锁
        if(!JAVA_VERSION_DETECTED){
            synchronized (JAVA_VERSION_DETECTION_LOCK){
                // 获取锁后，如果尚未检测，检测版本
                if(!JAVA_VERSION_DETECTED){
                    try {
                        //获取java版本
                        String javaVersion = System.getProperties().getProperty("java.version");
                        final int needVersion = 8;
                        boolean largerThan8 = Integer.parseInt(javaVersion.split("_")[0].split("\\.")[1]) >= needVersion;
                        if (!largerThan8) {
                            Colors colors = Colors.builder()
                                    .addNoColor("检测到您的版本号为 ")
                                    .add(javaVersion, Colors.FONT.RED)
                                    .addNoColor(", 小于")
                                    .add(" 1.8 ", Colors.FONT.BLUE)
                                    .add("版本。")
                                    .add("本框架基于1.8实现，推荐您切换为1.8或以上版本。")
                                    .build();
                            QQLog.warning(colors);
                            QQLog.warning("假如您的版本在1.8或以上，请忽略这两条信息。");
                        }
                    } catch (Exception e) {
                        QQLog.warning("java版本号检测失败, 请尽可能确保您的java版本在1.8以上");
                    }
                    JAVA_VERSION_DETECTED = true;
                }
            }
        }
    }

    /**
     * 没有监听函数的送信器
     */
    private MsgSender NO_METHOD_SENDER;

    /**
     * 注册器，赋值在扫描方法结束后
     */
    private Register register;

    /**
     * 依赖获取器，赋值在配置后
     */
    private DependGetter dependGetter;

//    /**
//     * 消息拦截器
//     */
//    private MsgIntercept[] msgIntercepts;

//    /**
//     * 送信拦截器
//     */
//    private SenderSendIntercept[] senderSendIntercepts = {};
//    private SenderSetIntercept[]  senderSetIntercepts  = {};
//    private SenderGetIntercept[]  senderGetIntercepts  = {};

    /**
     * 线程工厂初始化
     */
    protected void threadPoolInit(CONFIG config) {
        //创建并保存线程池
        ResourceDispatchCenter.saveThreadPool(config.getPoolConfig());
    }

    /**
     * 公共资源初始化
     */
    private void baseResourceInit() {
        //将CQCodeUtil放入资源调度中心
        ResourceDispatchCenter.saveCQCodeUtil(CQCodeUtil.build());
        //将ListenerMethodScanner放入资源调度中心
        ResourceDispatchCenter.saveListenerMethodScanner(new ListenerMethodScanner());
        //将ListenerFilter放入资源调度中心
        ResourceDispatchCenter.saveListenerFilter(new ListenerFilter());
    }

    /**
     * 定时任务初始化
     */
    private void timeTaskInit() {
        //将定时任务类添加到资源调度中心
        ResourceDispatchCenter.saveTimeTaskManager(new TimeTaskManager());
//        将定时任务工厂添加到资源调度中心
//        ResourceDispatchCenter.saveStdSchedulerFactory(new StdSchedulerFactory());
    }

    /**
     * 日志初始化
     * @param config 配置类
     */
    private void logInit(CONFIG config) {
        // 设置日志输出等级
        QQLog.setGlobalLevel(config.getLogLevel());
        _hello$();
    }

    /**
     * 语言初始化
     * @param app    启动器接口实现类
     * @param config 配置类
     */
    private void languageInit(Application<CONFIG> app, CONFIG config){
        ClassLoader classLoader = app.getClass().getClassLoader();
        Locale language = config.getLanguage();
        // 语言初始化
        Language.init(classLoader, language);
    }

    /**
     * 对fastJson进行配置
     */
    private void fastJsonInit() {
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
    }

    /**
     * 开发者实现的资源初始化
     * 此方法将会在所有的初始化方法最后执行
     * 增加一个参数
     * 此资源配置将会在配置之后执行
     */
    protected abstract void resourceInit(CONFIG config);

    /**
     * 开发者实现的资源初始化
     * 此方法将会在所有的初始化方法最后执行
     * 这个没有参数的将会在配置之前执行，建议从此处配置配置类实例化
     */
    protected abstract void resourceInit();

    //**************** 获取三种送信器 ****************//

    /**
     * 获取消息发送接口, 将会在连接成功后使用
     */
    protected abstract SenderSendList getSender();

    /**
     * 获取事件设置接口, 将会在连接成功后使用
     */
    protected abstract SenderSetList getSetter();

    /**
     * 获取资源获取接口, 将会在连接成功后使用
     */
    protected abstract SenderGetList getGetter();

    /**
     * 获取特殊API对象
     */
    public abstract SP_API getSpecialApi();

    /**
     * 开发者实现的启动方法
     * v1.1.2-BETA后返回值修改为String，意义为启动结束后打印“启动成功”的时候使用的名字
     * 例如，返回值为“server”，则会输出“server”启动成功
     * <p>
     * v1.4.1之后增加一个参数：dependCenter
     *
     * @param dependCenter 依赖管理器，可以支持组件额外注入部分依赖。
     * @param manager      监听管理器，用于分配获取到的消息
     */
    protected abstract String start(DependCenter dependCenter, ListenerManager manager);

    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     * 此方法将会最先被执行
     */
    protected abstract CONFIG getConfiguration();


    //**************** 以下是一些不强制但是可以通过重写来拓展功能的方法 ****************//

    /**
     * 依赖扫描之前
     *
     * @param config   配置文件
     * @param app      启动器接口实现类
     * @param register 注册器
     */
    protected void beforeDepend(CONFIG config, Application<CONFIG> app, Register register) {
    }

    /**
     * 依赖扫描之后
     * 同时也是监听函数扫描之前
     *
     * @return 所有的执行任务
     */
    protected void afterDepend(CONFIG config, Application<CONFIG> app, Register register, DependCenter dependCenter) {
    }


    /**
     * 监听函数扫描之后
     *
     * @return 所有的执行任务
     */
    protected Consumer<Class<?>[]>[] afterListener(CONFIG config, Application<CONFIG> app) {
        return null;
    }

    /**
     * 服务启动前
     */
    protected void beforeStart() {
    }

    /**
     * 服务启动后, 构建无参数送信器之前
     */
    protected void afterStart() {
    }

    /**
     * 监听函数注册之前，可以执行重写并进行额外的监听注入
     */
    protected void beforeRegisterListener(CONFIG config, Application<CONFIG> app, ListenerMethodScanner scanner, DependCenter dependCenter) {

    }


    /**
     * 初始化
     */
    private void init(Application<CONFIG> app, CONFIG config) {
        //日志初始化
        logInit(config);
        // 语言初始化
        languageInit(app, config);
        //配置fastJson
        fastJsonInit();
        //公共资源初始化
        baseResourceInit();
        //线程工厂初始化
        threadPoolInit(config);
        //定时任务初始化
        timeTaskInit();
        //资源初始化
        resourceInit(config);
    }

    /**
     * 进行扫描
     */
    private ScannerManager scanner(Set<String> packages) {
        //使用扫描管理器进行扫描
        return ScannerManager.scanner(packages);
    }

    /**
     * 配置结束后的方法
     */
    private DependCenter afterConfig(CONFIG config, Application<CONFIG> app) {

        //构建监听扫描器
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();

        // 扫描并获取依赖中心
        DependCenter dependCenter = scanAndInject(config, app);

        // 注册监听函数
        registerListener(config, app, scanner, dependCenter);

        //根据配置类的扫描结果来构建监听器管理器和阻断器
        // 准备获取消息拦截器

        RUN_LOG.debug("intercept.msg.prepare");
        MsgIntercept[] msgIntercepts = register.performingTasks(
                c -> FieldUtils.isChild(c, MsgIntercept.class),
                (Class<?>[] cs) -> Arrays.stream(cs)
//                        .peek(c -> QQLog.debug("加载消息拦截器: " + c))
                        .peek(c -> RUN_LOG.info("intercept.msg.load", c))
                        .map(dependCenter::get).filter(Objects::nonNull)
                        .toArray(MsgIntercept[]::new)
        );
        if(msgIntercepts == null || msgIntercepts.length == 0){
            RUN_LOG.debug("intercept.msg.empty");
        }

        // 构建管理中心
        ListenerManager manager = scanner.buildManager(msgIntercepts);
        // 构建阻断器
        Plug plug = scanner.buildPlug();

        //保存
        ResourceDispatchCenter.saveListenerManager(manager);
        ResourceDispatchCenter.savePlug(plug);


        //准备截器
        RUN_LOG.debug("intercept.sender.prepare");
        SenderSendIntercept[] senderSendIntercepts = register.performingTasks(
                c -> FieldUtils.isChild(c, SenderSendIntercept.class),
                (Class<?>[] cs) -> Arrays.stream(cs)
                        .peek(c -> RUN_LOG.debug("intercept.sender.load", c))
                        .map(dependCenter::get).filter(Objects::nonNull)
                        .toArray(SenderSendIntercept[]::new)
        );
        if(senderSendIntercepts == null || senderSendIntercepts.length == 0){
            RUN_LOG.debug("intercept.sender.empty");
        }
        //********************************//

        RUN_LOG.debug("intercept.setter.prepare");
        SenderSetIntercept[] senderSetIntercepts = register.performingTasks(
                c -> FieldUtils.isChild(c, SenderSetIntercept.class),
                (Class<?>[] cs) -> Arrays.stream(cs)
                        .peek(c -> RUN_LOG.debug("intercept.setter.load", c))
                        .map(dependCenter::get).filter(Objects::nonNull)
                        .toArray(SenderSetIntercept[]::new)
        );
        if(senderSetIntercepts == null || senderSetIntercepts.length == 0){
            RUN_LOG.debug("intercept.setter.empty");
        }
        //********************************//

        RUN_LOG.debug("intercept.getter.prepare");
        SenderGetIntercept[] senderGetIntercepts = register.performingTasks(
                c -> FieldUtils.isChild(c, SenderGetIntercept.class),
                (Class<?>[] cs) -> Arrays.stream(cs)
                        .peek(c -> RUN_LOG.debug("intercept.getter.load", c))
                        .map(dependCenter::get).filter(Objects::nonNull)
                        .toArray(SenderGetIntercept[]::new)
        );
        if(senderGetIntercepts == null || senderGetIntercepts.length == 0){
            RUN_LOG.debug("intercept.getter.empty");
        }
        //*******************************//

        senderSendIntercepts = senderSendIntercepts == null ? new SenderSendIntercept[0] : senderSendIntercepts;
        senderSetIntercepts  = senderSetIntercepts  == null ? new SenderSetIntercept [0] : senderSetIntercepts ;
        senderGetIntercepts  = senderGetIntercepts  == null ? new SenderGetIntercept [0] : senderGetIntercepts ;

        // 送信拦截器直接变更MsgSender的实例化过程
        MsgSender.setSenderSendIntercepts(senderSendIntercepts);
        MsgSender.setSenderSetIntercepts(senderSetIntercepts);
        MsgSender.setSenderGetIntercepts(senderGetIntercepts);

        //返回依赖管理器
        return dependCenter;
    }

    /**
     * 注册监听函数
     *
     * @param config       配置类
     * @param app          启动器接口实现类
     * @param scanner      扫描器
     * @param dependCenter 依赖中心
     */
    private void registerListener(CONFIG config, Application<CONFIG> app, ListenerMethodScanner scanner, DependCenter dependCenter) {

        // > 监听函数注册之前
        beforeRegisterListener(config, app, scanner, dependCenter);

        //直接注册监听函数
        this.register.registerListener(scanner);

        // > 监听函数注册之后
        Consumer<Class<?>[]>[] afterListenerConsumer = afterListener(config, app);
        if (afterListenerConsumer != null) {
            for (Consumer<Class<?>[]> c : afterListenerConsumer) {
                register.performingTasks(c);
            }
        }
    }

    /**
     * 进行依赖扫描与注入
     *
     * @return 依赖中心
     */
    private DependCenter scanAndInject(CONFIG config, Application<CONFIG> app) {
        //包路径
        String appPackage = app.getClass().getPackage().getName();
        Set<String> scanAllPackage = new HashSet<>();

        //配置完成后，如果没有进行扫描，则默认扫描启动类同级包
        //需要扫描的包路径，如果是null则扫描启动器的根路径，否则按照要求进行扫描
        Set<String> scannerPackage = config.getScannerPackage();

        //查看启动类上是否存在@AllBeans注解
        AllBeans annotation = AnnotationUtils.getAnnotation(app.getClass(), AllBeans.class);
        if (annotation != null) {
            //如果存在全局包扫描
            String[] value = annotation.value();
            if (value.length == 0) {
                scanAllPackage.add(appPackage);
            } else {
                scanAllPackage = Arrays.stream(value).collect(Collectors.toSet());
            }

        }

        //包扫描路径，如果没有且类上没有全局搜索注解，则默认扫描启动类下包
        if ((scannerPackage == null || scannerPackage.isEmpty())) {
            scannerPackage = new HashSet<String>() {{
                add(appPackage);
            }};
        }

        //**************** 执行扫描 ****************//
        //进行扫描并保存注册器
        this.register = scanner(scannerPackage);

        //**************** 配置依赖注入相关 ****************//
        //配置依赖管理器
        //将依赖管理对象放入资源管理中心
        DependGetter dependGetter = getConfiguration().getDependGetter();

        //此处可以尝试去寻找被扫描到的接口对象
        // 寻找携带@Config且实现了Dependgetter的类
        if (dependGetter == null) {
            dependGetter = register.performingTasks(
                    //过滤出携带者Config注解的、不是接口和抽象类的、是DependGetter的子类的
                    c -> (AnnotationUtils.getAnnotation(c, Config.class) != null) &&
                            (FieldUtils.notInterfaceAndAbstract(c)) && (FieldUtils.isChild(c, DependGetter.class)),
                    //看看有没有，如果有，赋值。
                    cs -> {
                        if (cs.length == 1) {
                            //找到一个，尝试实例化
                            Class<?> c = cs[0];
                            try {
                                return (DependGetter) BeansUtils.getInstance(c);
                            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                                return null;
                            }
                        } else if (cs.length == 0) {
                            return null;
                        } else {
                            throw new RobotRunException("moreDepends", DependGetter.class);
                        }
                    });
        }

        // > 依赖扫描之前
        beforeDepend(config, app, register);


        DependCenter dependCenter = dependGetter == null ? new DependCenter() : new DependCenter(dependGetter);
        ResourceDispatchCenter.saveDependCenter(dependCenter);

        //赋值
        this.dependGetter = dependCenter;

        // ***** 注入一些其他的东西且无视异常 ***** //

        // 注入自己
        dependCenter.loadIgnoreThrow(dependCenter);
        // 注入CQCodeUtil
        dependCenter.loadIgnoreThrow(CQCodeUtil.build());
        // 注入当前这个启动器
        dependCenter.loadIgnoreThrow(this);
        // 注入配置类
        dependCenter.loadIgnoreThrow(config);


        //如果有全局注入，先扫描并注入全局注入
        if (annotation != null) {
            //获取扫描器
            FileScanner fileScanner = new FileScanner();
            //扫描
            for (String p : scanAllPackage) {
                //全局扫描中，如果存在携带@beans的注解，则跳过.
                //全局扫描只能将不存在@Beans注解的依赖进行添加
                fileScanner.find(p, c -> AnnotationUtils.getBeansAnnotationIfListen(c) == null);
            }
            //获取扫描结果
            Set<Class<?>> classes = fileScanner.get();
            ScannerManager.getInstance(classes).registerDependCenterWithoutAnnotation(annotation.beans());
        }

        //注入依赖-普通的扫描依赖
        this.register.registerDependCenter(dependCenter);


        // > 依赖注入之后
        afterDepend(config, app, this.register, dependCenter);

        return dependCenter;
    }

    /**
     * 有些事情需要连接之后才能做，例如加载定时任务，需要空函数送信器
     */
    private void after() {
        //注册监听函数
        this.register.registerTimeTask(this.NO_METHOD_SENDER);
    }

    /**
     * 展示系统信息
     */
    private void showSystemInfo(CONFIG config){
        //# 启动时候的系统类型展示
        //run.os.name=系统名称: {0}
        //run.os.version=系统版本: {0}
        RUN_LOG.info("os.name", System.getProperty("os.name"));
        RUN_LOG.info("os.version", System.getProperty("os.version"));
        // 线程池信息
        BaseLocalThreadPool.PoolConfig poolConfig = config.getPoolConfig();
        RUN_LOG.info("thread.blockingFactor", config.getBlockingFactor());
        RUN_LOG.info("thread.size", poolConfig.getCorePoolSize());
        RUN_LOG.info("thread.maxSize", poolConfig.getMaximumPoolSize());

    }

    /**
     * 执行的主程序
     * @param app 启动器接口的实现类
     * @param args 可能会有用的额外指令参数，一般是main方法的参数
     */
    public void run(Application<CONFIG> app, String... args) {
        //无配置资源初始化
        resourceInit();

        //获取配置对象
        CONFIG configuration = getConfiguration();

        //用户进行配置
        app.before(configuration);

        //初始化
        init(app, configuration);

        // 展示系统信息
        showSystemInfo(configuration);

        //配置结束, 获取依赖管理器
        DependCenter dependCenter = afterConfig(configuration, app);

        //获取管理器
        ListenerManager manager = ResourceDispatchCenter.getListenerManager();


        // > 启动之前
        beforeStart();


        //开始连接
        long s = System.currentTimeMillis();

        String name = start(dependCenter, manager);

        long e = System.currentTimeMillis();
//        String msg = name + "启动成功,耗时(" + (e - s) + "ms)";
        String msg = "start.success";
        RUN_LOG.info(msg, Colors.builder().add(name, Colors.FONT.DARK_GREEN).build(), e - s);


        // > 启动之后
        afterStart();


        //获取CQCodeUtil实例
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();
        //构建没有监听函数的送信器并保存
        MsgSender sender = MsgSender.build(getSender(), getSetter(), getGetter());
        this.NO_METHOD_SENDER = sender;
        // MsgSender存入依赖中心
        dependCenter.loadIgnoreThrow(sender);

        after();

        //连接之后
        app.after(cqCodeUtil, sender);
    }


    //**************** 部分资源获取API ****************//

    /**
     * 获取依赖获取器
     */
    public DependGetter getDependGetter() {
        return this.dependGetter;
    }

    /**
     * 获取空函数送信器<br>
     * ※ 此送信器无法进行阻断
     */
    public MsgSender getMsgSender() {
        return this.NO_METHOD_SENDER;
    }


    //**************** 构造 ****************//

    /**
     * 无参构造
     */
    public BaseApplication() {
    }

    /**
     * 日志拦截构造
     */
    public BaseApplication(QQLogBack qqLogBack) {
        QQLog.changeQQLogBack(qqLogBack);
    }


    /**
     * <pre> 使得这个方法可以被覆盖。
     * <pre> 别吐槽里面的变量名了。
     * @since  1.7.x
     */
    protected void _hello$(){

        String sp1 = Colors.builder().add(' ', wowThatIsRainbowToo$()).add(' ', wowThatIsRainbowToo$()).build().toString();
        String sp2 = Colors.builder().add(' ', wowThatIsRainbowToo$()).add(' ', wowThatIsRainbowToo$()).build().toString();

        String oh_hi_is_me = "_(^w^)L~~ by simple-robot@ForteScarlet ~~";
        int length = oh_hi_is_me.length() + 4;
        char line = ' ';
        /* QQLog初始化的时候输出个东西~ */
        ColorsBuilder hi_i_am_builder_HEAD = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_HEAD.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_HEAD.build().toString());

        ColorsBuilder hi_i_am_builder = Colors.builder();
        oh_hi_is_me.chars().forEach(ic -> hi_i_am_builder.add((char) ic, wowThatIsRainbow$()));
        System.out.println(sp1 + hi_i_am_builder.build().toString() + sp2);
        ColorsBuilder hi_i_am_builder_END = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_END.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_END.build().toString());
    }
    private ColorTypes wowThatIsRainbow$(){
        return RandomUtil.getRandomElement(FontColorTypes.values());
    }
    private ColorTypes wowThatIsRainbowToo$(){
        return RandomUtil.getRandomElement(BackGroundColorTypes.values());
    }
}
