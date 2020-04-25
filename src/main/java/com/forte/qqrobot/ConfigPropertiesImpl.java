package com.forte.qqrobot;

import java.util.Properties;
import java.util.Set;

/**
 * {@link ConfigProperties}的默认实现
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConfigPropertiesImpl implements ConfigProperties {

    private Properties properties;

    public ConfigPropertiesImpl(Properties properties){
        this.properties = properties;
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Set<String> keys() {
        return properties.stringPropertyNames();
    }


}
