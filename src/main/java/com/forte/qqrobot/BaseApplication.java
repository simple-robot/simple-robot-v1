package com.forte.qqrobot;

import com.alibaba.fastjson.util.TypeUtils;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.anno.depend.AllBeans;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.listener.DefaultWholeListener;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.ListenerMethodScanner;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.safe.PoliceStation;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.scanner.Register;
import com.forte.qqrobot.scanner.ScannerManager;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.forte.qqrobot.timetask.TimeTaskManager;
import com.forte.qqrobot.utils.BaseLocalThreadPool;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.impl.StdSchedulerFactory;

import java.io.Closeable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 启动类总抽象类，在此实现部分通用功能
 * 实现closeable接口
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:18
 * @since JDK1.8
 **/
public abstract class BaseApplication<CONFIG extends BaseConfiguration> implements Closeable {

    //java版本检测
    static{
        //初始化警察局
        PoliceStation.getInstance();

        try{
            //获取java版本
            String javaVersion = System.getProperties().getProperty("java.version");
            final int needVersion = 8;
            boolean largerThan8 = Integer.parseInt(javaVersion.split("_")[0].split("\\.")[1]) >= needVersion;
            if(!largerThan8){
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
        }catch (Exception e){
            QQLog.warning("java版本号检测失败, 请尽可能确保您的java版本在1.8以上");
        }


    }

    /** 没有监听函数的送信器 */
    private MsgSender NO_METHOD_SENDER;

    /** 注册器，赋值在扫描方法结束后 */
    private Register register;

    /** 依赖获取器，赋值在配置后 */
    private DependGetter dependGetter;

    /**
     * 线程工厂初始化
     */
    protected void threadPoolInit(){
        BaseLocalThreadPool.setTimeUnit(TimeUnit.SECONDS);
        //空线程存活时间
        BaseLocalThreadPool.setKeepAliveTime(60);
        //线程池的线程工厂
        BaseLocalThreadPool.setDefaultThreadFactory(Thread::new);
        //核心池数量，可同时执行的线程数量
        BaseLocalThreadPool.setCorePoolSize(500);
        //线程池最大数量
        BaseLocalThreadPool.setMaximumPoolSize(1200);
        //对列策略
        BaseLocalThreadPool.setWorkQueue(new LinkedBlockingQueue<>());
    }

    /**
     * 公共资源初始化
     */
    private void baseResourceInit(){
        //将CQCodeUtil放入资源调度中心
        ResourceDispatchCenter.saveCQCodeUtil(CQCodeUtil.build());
        //将DefaultWholeListener放入资源调度中心
        ResourceDispatchCenter.saveDefaultWholeListener(new DefaultWholeListener());
        //将ListenerMethodScanner放入资源调度中心
        ResourceDispatchCenter.saveListenerMethodScanner(new ListenerMethodScanner());

        //将ListenerFilter放入资源调度中心
        ResourceDispatchCenter.saveListenerFilter(new ListenerFilter());
    }

    /**
     * 定时任务初始化
     */
    private void timeTaskInit(){
        //将定时任务类添加到资源调度中心
        ResourceDispatchCenter.saveTimeTaskManager(new TimeTaskManager());
        //将定时任务工厂添加到资源调度中心
        ResourceDispatchCenter.saveStdSchedulerFactory(new StdSchedulerFactory());
    }

    /**
     * 对fastJson进行配置
     */
    private void fastJsonInit(){
        //设置FastJson配置，使FastJson不会将开头大写的字段默认变为小写
        TypeUtils.compatibleWithJavaBean = true;
    }

    /**
     * 开发者实现的资源初始化
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
     * 开发者实现的启动方法
     * v1.1.2-BETA后返回值修改为String，意义为启动结束后打印“启动成功”的时候使用的名字
     * 例如，返回值为“server”，则会输出“server”启动成功
     * @param manager 监听管理器，用于分配获取到的消息
     */
    protected abstract String start(ListenerManager manager);

    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     */
    protected abstract CONFIG getConfiguration();

    /**
     * 初始化
     */
    private void init(){
        //配置fastJson
        fastJsonInit();
        //公共资源初始化
        baseResourceInit();
        //资源初始化
        resourceInit();
        //线程工厂初始化
        threadPoolInit();
        //定时任务初始化
        timeTaskInit();
    }

    /**
     * 进行扫描
     */
    private Register scanner(Set<String> packages){
        //使用扫描管理器进行扫描
        return ScannerManager.scanner(packages);
    }

    /**
     * 配置结束后的方法
     */
    private void afterConfig(CONFIG config, Application<CONFIG> app){
        //先配置依赖管理器
        //将依赖管理对象放入资源管理中心
        DependGetter dependGetter = CONFIG.getDependGetter();
        DependCenter dependCenter = dependGetter == null ? new DependCenter() : new DependCenter(dependGetter);
        ResourceDispatchCenter.saveDependCenter(dependCenter);
        //赋值
        this.dependGetter = dependCenter;

        String appPackage = app.getClass().getPackage().getName();

        Set<String> scanAllPackage = new HashSet<>();

        //配置完成后，如果没有进行扫描，则默认扫描启动类同级包
        //需要扫描的包路径，如果是null则扫描启动器的根路径，否则按照要求进行扫描
        Set<String> scannerPackage = config.getScannerPackage();

        //查看启动类上是否存在@AllBeans注解
        AllBeans annotation = app.getClass().getAnnotation(AllBeans.class);
        if(annotation != null){
            //如果存在全局包扫描
            String[] value = annotation.value();
            if(value.length == 0){
                scanAllPackage.add(appPackage);
            }else{
                scanAllPackage = Arrays.stream(value).collect(Collectors.toSet());
            }

        }

        //包扫描路径，如果没有且类上没有全局搜索注解，则默认扫描启动类下包
        if((scannerPackage == null || scannerPackage.isEmpty())){
            scannerPackage = new HashSet<String>(){{
                add(appPackage);
            }};
        }

        //

        //进行扫描并保存注册器
        //这个是普通的依赖注入
        this.register = scanner(scannerPackage);

        //如果有全局注入，先扫描并注入全局注入
        if(annotation != null){
            //获取扫描器
            FileScanner fileScanner = new FileScanner();
            //扫描
            for (String p : scanAllPackage) {
                //全局扫描中，如果存在携带@beans的注解，则跳过.
                //全局扫描只能将不存在@Beans注解的依赖进行添加
                fileScanner.find(p, c -> c.getAnnotation(Beans.class) == null);
            }
            //获取扫描结果
            Set<Class<?>> classes = fileScanner.get();
            ScannerManager.getInstance(classes).registerDependCenterWithoutAnnotation(annotation.beans());
        }

        //注入依赖-普通的扫描依赖
        this.register.registerDependCenter();


        //直接注册监听函数
        this.register.registerListener();


        //构建监听函数管理器等扫描器所构建的
        ListenerMethodScanner scanner = ResourceDispatchCenter.getListenerMethodScanner();

        //根据配置类的扫描结果来构建监听器管理器和阻断器
        ListenerManager manager = scanner.buildManager();
        Plug plug = scanner.buildPlug();

        //保存
        ResourceDispatchCenter.saveListenerManager(manager);
        ResourceDispatchCenter.savePlug(plug);
    }

    /**
     * 有些事情需要连接之后才能做，例如加载定时任务，需要空函数送信器
     */
    private void after(){
        //注册监听函数
        this.register.registerTimeTask(this.NO_METHOD_SENDER);
    }

    /**
     * 执行的主程序
     */
    public void run(Application<CONFIG> app){
        //先初始化
        init();

        //获取配置对象
        CONFIG configuration = getConfiguration();
        //开始之前
        app.before(configuration);

        //配置结束
        afterConfig(configuration, app);

        //获取管理器
        ListenerManager manager = ResourceDispatchCenter.getListenerManager();

        //开始连接
        long s = System.currentTimeMillis();
        String name = start(manager);
        long e = System.currentTimeMillis();
        String msg = name + "启动成功,耗时(" + (e - s) + "ms)";
        QQLog.info(Colors.builder().add(msg, Colors.FONT.DARK_GREEN).build());


        //获取CQCodeUtil实例
        CQCodeUtil cqCodeUtil = ResourceDispatchCenter.getCQCodeUtil();
        //构建没有监听函数的送信器并保存
        MsgSender sender = MsgSender.build(getSender(), getSetter(), getGetter());
        this.NO_METHOD_SENDER = sender;

        //连接之后的收尾工作
        after();

        //连接之后
        app.after(cqCodeUtil, sender);
    }


    //**************** 部分资源获取API ****************//

    /**
     * 获取依赖获取器
     */
    public DependGetter getDependGetter(){
        return this.dependGetter;
    }

    /**
     * 获取空函数送信器<br>
     * ※ 此送信器无法进行阻断
     */
    public MsgSender getMsgSender(){
        return this.NO_METHOD_SENDER;
    }




}
