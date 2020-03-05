package com.forte.qqrobot;

import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.apache.http.impl.io.EmptyInputStream;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * 使用注解自动装配的资源启动器
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
class AutoResourceApplication<CONFIG extends BaseConfiguration> implements ResourceApplication<CONFIG> {

    private String resources;
    private Properties properties;
    private Class<?> baseClass;

    /**
     * 通过Application注解与Config注解转化一个启动器接口
     * @param configClass               配置类的类型class
     * @param applicationAnnotation     启动器注解，不能为null
     * @param configurationAnnotation   启动器上的注解配置，可以为null
     * @return 自动资源配置启动器
     */
    static <CONFIG extends BaseConfiguration> AutoResourceApplication<CONFIG> autoConfig(Class<CONFIG> configClass,
                                                                                         SimpleRobotApplication applicationAnnotation,
                                                                                         SimpleRobotConfiguration configurationAnnotation,
                                                                                         Class<?> baseClass){
        String resources = applicationAnnotation.resources().trim();
        Properties properties = new Properties();

        // load config
        if(configurationAnnotation != null){
            ConfigurationProperty[] value = configurationAnnotation.value();
            for (ConfigurationProperty property : value) {
                String k = property.key();
                String v = property.value();
                if(k.trim().length() > 0 && v.trim().length() > 0){
                    properties.setProperty(k, v);
                }
            }
        }
        // 返回实例对象
        return new AutoResourceApplication<>(resources, properties, baseClass);
    }

    /**
     * 构造
     * @param resources  resources配置文件路径 不可为null
     * @param properties 额外的参数, 可以为null
     */
    AutoResourceApplication(String resources, Properties properties, Class<?> baseClass){
        this.resources = resources;
        this.properties = properties;
        this.baseClass = baseClass;
    }

    public String resourceName(){
        return resources;
    }

    /**
     * 获取启动器类所在包路径
     * @return 启动器类包路径
     */
    @Override
    public Package getPackage(){
        return baseClass.getPackage();
    }

    /**
     * 获取作为application的class类
     */
    @Override
    public Class<?> getApplicationClass(){
        return baseClass;
    }

    /**
     * 获取配置文件的文件输入流对象，并根据此对象对配置对象进行自动装填。
     * 获取到的流在使用完成后会自动关闭。
     *
     * @return 配置文件流对象（properties
     */
    @Override
    public InputStream getStream() {
        // 如果资源路径为空字符串，则读取一个空值，即不进行文件读取
        if(resources.length() == 0){
            return EmptyInputStream.INSTANCE;
        }
        InputStream stream = this.getClass().getResourceAsStream(resourceName());
        return Objects.requireNonNull(stream, "未读取到配置文件 : resource inputstream is null.");
    }

    @Override
    public void plus(Properties configProperties){
        // 追加参数
        if(properties != null){
            Set<String> keys = properties.stringPropertyNames();
            for (String key : keys) {
                configProperties.setProperty(key, properties.getProperty(key));
            }
        }
    }

    /**
     * 此方法将会在配置文件装配完成后执行.
     * 所以如果这个时候更改Properties是 没有用的~没有用的~
     *
     * @param args          properties配置内容
     * @param configuration 配置好的配置文件
     */
    @Override
    public void before(Properties args, CONFIG configuration) { }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) { }
}
