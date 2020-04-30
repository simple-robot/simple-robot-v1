package com.forte.qqrobot;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;
import com.forte.qqrobot.exception.ConfigurationException;
import com.forte.qqrobot.log.QQLog;

import java.io.*;
import java.util.Properties;

/**
 *
 * 对用户配置接口的拓展，提供读取配置文件的形式来对配置类进行自动装填。
 * 目前仅支持properties类型的配置文件。
 * 数组类型的参数使用逗号','分隔。
 * 其他特殊的配置类在出现其他优化方式之前，可以暂时使用新提供的before方法进行额外配置。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ResourceApplication<CONFIG extends BaseConfiguration> extends Application<CONFIG> {


    /**
     * 获取配置文件的文件输入流对象，并根据此对象对配置对象进行自动装填。
     * 获取到的流在使用完成后会自动关闭。
     * @return 配置文件流对象（properties
     */
    InputStream getStream();

    /**
     * 获取Reader对象。
     * Properties在读取的时候，使用字节流无法读取中文，因此可以考虑使用getReader来进行转化。
     * @return 配置文件Reader对象
     */
    default Reader getReader(){
        return new BufferedReader(new InputStreamReader(getStream()));
    };

    /**
     * default方法默认实现此方法, 请不要再实现此方法了。
     * 流对象使用完毕时候会自动关流。
     * @param configuration　配置类对象
     */
    @Override
    default void before(CONFIG configuration) {
        Properties configProperties = new Properties();

        // 获取流并自动关闭
        try (Reader reader = getReader()) {
            configProperties.load(reader);
        } catch (IOException e) {
            throw new ConfigurationException("properties 配置流读取错误: ", e);
        } catch (NullPointerException e) {
            throw new ConfigurationException("properties 配置流读不可为NULL: ", e);
        }

        // 配置读取完成，inject config
        Class<CONFIG> configType = (Class<CONFIG>) configuration.getClass();
        InjectableConfig<CONFIG> injectableConfig = ConfigurationHelper.toInjectable(configType);

        // 额外操作
        plus(configProperties);

        // 注入配置
        injectableConfig.inject(configuration, configProperties);
        QQLog.info("properties config injected.");

        // 设置配置properties
        configuration.setConfigProperties(new ConfigProperties(configProperties));

        // 执行之后的before
        before(configProperties, configuration);
    }

    /**
     * 如果在配置进行注入的时候有什么额外的操作，重写此方法，否则不要重写。
     * @param configProperties config 参数列表
     */
    default void plus(Properties configProperties){}

    /**
     * 此方法将会在配置文件装配完成后执行.
     * 所以如果这个时候更改Properties是 没有用的~没有用的~
     * @param args          properties配置内容
     * @param configuration 配置好的配置文件
     */
    void before(Properties args, CONFIG configuration);


}
