/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ConfigProperties.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 配置文件读取到的Peoperties信息
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConfigProperties extends Properties {

    /** 使用流读取的时候，默认使用utf-8编码读取 */
    private static final Charset defaultCharset = StandardCharsets.UTF_8;

    public ConfigProperties(){
    }
    public ConfigProperties(Properties p){
        for (String k : p.stringPropertyNames()) {
            setProperty(k, p.getProperty(k));
        }
    }

    /**
     * override this load method
     */
    @Override
    public void load(Reader reader) throws IOException {
        super.load(new BufferedReader(reader));
    }
    /**
     * override this load method
     */
    @Override
    public void load(InputStream inputStream) throws IOException {
        load(new BufferedReader(new InputStreamReader(inputStream, defaultCharset)));
    }
    /**
     * override this load method
     */
    public void load(InputStream inputStream, String charset) throws IOException {
        load(new BufferedReader(new InputStreamReader(inputStream, charset)));
    }

    /**
     * key-value foreach
     */
    public void foreach(BiConsumer<String, String> foreachConsumer){
        Set<String> keys = stringPropertyNames();
        for (String key : keys) {
            foreachConsumer.accept(key, getProperty(key));
        }
    }

    /**
     * 根据{@link com.forte.config.Conf}的注解信息以及配置文件中的内容将配置信息注入到此类中。
     */
    public <T> void injectToConfig(T config){
        injectToConfig(config, (Class<T>) config.getClass());
    }

    /**
     * 根据{@link com.forte.config.Conf}的注解信息以及配置文件中的内容将配置信息注入到此类中。
     * 手动指定Class类对象
     */
    public <T> void injectToConfig(T config, Class<T> configType){
        InjectableConfig<T> injectableConfig = ConfigurationHelper.toInjectable(configType);
        injectableConfig.inject(config, this);
    }

}
