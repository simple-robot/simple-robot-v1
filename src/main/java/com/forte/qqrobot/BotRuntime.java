package com.forte.qqrobot;

import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.LoginInfo;
import com.forte.qqrobot.exception.RobotDevException;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.ListenerInfo;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogLang;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * <pre> 应当于启动初期就初始化的
 * <pre> 运行期间的信息，启动后进行初始化并注入至依赖中心。
 * <pre> 运行期间, 内部所使用的实例应当只存在一个实例。
 * <pre> 可以对其进行覆盖，来实现分布式的登录信息同步。
 * <pre> 可以预见的，这个类会在后续的版本更新中被频繁更改。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BotRuntime {

    /**
     * 应当唯一存在的runtime对象
     */
    private static volatile BotRuntime runtime;

    /**
     * 日志的语言前缀：runtime
     */
    private final QQLogLang log = new QQLogLang("runtime");


    private Collection<ListenerInfo> listenerInfos;
    private BaseConfiguration configuration;

    /**
     * bot信息管理器
     */
    private BotManager botManager;

    /**
     * <pre> 构建要给RuntimeInfo实例，目前需要的参数：
     * <pre> listenerInfo信息，一般在扫描结束后可以得到
     * <pre> botInfo信息，一般在配置完成后，构架完GETTER后可以得到
     * @param listenerInfos 监听器信息
     * @param botManager     注册的bot信息
     * @param configuration 配置信息，最终会得到一份复印份
     */
    private BotRuntime(Collection<ListenerInfo> listenerInfos, BotManager botManager, BaseConfiguration configuration) {
        this.listenerInfos = listenerInfos;
        this.botManager = botManager;
        // clone配置类
        this.configuration = configuration;
    }

    /**
     * 获取语言日志对象，语言在此类实例化结束后应当已经初始化完毕。
     */
    protected QQLogLang getLog() {
        return log;
    }

    /**
     * 初始化runtime对象
     *
     * @return 初始化的结果
     */
    public static synchronized BotRuntime initRuntime(
            // 初始化所需要的资源
            Collection<ListenerInfo> listenerInfos, BotInfo[] botInfos, BaseConfiguration configuration,
            // 部分数据存放资源
            // botManager获取器
            Supplier<BotManager> botManagerSupplier
    ) throws CloneNotSupportedException {
        // 已经初始化过了
        if (runtime != null) {
            throw new RobotRuntimeException(0, "botRuntime has already initialized!");
        }

        // 初始化唯一对象
        BotManager botManager = botManagerSupplier.get();
        // 注册信息
        for (BotInfo botInfo : botInfos) {
            if (botManager.registerBot(botInfo)) {
                LoginInfo info = botInfo.getInfo();
                String name = info.getName() + "(" + info.getCode() + ")";
                // 注册成功
                QQLog.info("runtime.bot.register", name);
            } else {
                // 注册失败
                QQLog.warning("runtime.bot.register.failed", botInfo.getBotCode());
            }
        }

        runtime = new BotRuntime(listenerInfos, botManager, (BaseConfiguration) configuration.clone());
        return runtime;
    }

    /**
     * 获取Runtime，如果尚未初始化则抛出异常。
     *
     * @return
     */
    public static BotRuntime getRuntime() {
        if (runtime == null) {
            throw new RobotRuntimeException(0, "botRuntime has not initialized!");
        }
        return runtime;
    }

    /**
     * 获取BotManager
     *
     * @return botManager
     */
    public BotManager getBotManager() {
        return botManager;
    }

    public BaseConfiguration getConfiguration() {
        return configuration;
    }

}
