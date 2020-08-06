/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AutoDependReader.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.depend;

import com.forte.qqrobot.exception.ModuleException;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.ReaderProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 自动装配配置读取器，静态方法即可。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AutoDependReader {

    /**
     * 读取所有的simbotFactory文件，并转化为Properties列表
     */
    public static Properties[] readModuleFactories(ClassLoader loader) throws IOException {
        String resourcePath = "simbot/factory/module.factory";
        List<Properties> plist = new ArrayList<>();

        Enumeration<URL> resources = loader.getResources(resourcePath);


        while (resources.hasMoreElements()){
            URL nextResource = resources.nextElement();
            QQLog.debug("resources.module.load", nextResource);
            ReaderProperties moduleProperties = new ReaderProperties();
            try(InputStream inputStream = nextResource.openStream()) {
                moduleProperties.load(inputStream);
            }
            if(moduleProperties.size() > 0){
                plist.add(moduleProperties);
            }
        }
        return plist.toArray(new Properties[0]);
    }

    /**
     * 从properties中提取出需要扫描的包路径。
     * @param moduleProperties module的properties
     */
    public static String[] modulePropertyScan(Properties moduleProperties){
        String key = "load.scan";
        String scanPackages = moduleProperties.getProperty(key);
        if(scanPackages == null){
            return new String[0];
        }else if(scanPackages.trim().length() == 0){
            return new String[0];
        }else{
            return Arrays.stream(scanPackages.split(","))
                    .map(String::trim)
                    .filter(p -> p.length() > 0)
                    .peek(p -> QQLog.debug("resources.module.load.scan", p))
            .toArray(String[]::new);
        }
    }

    /**
     * 获取需要加载的类
     */
    public static Class<?>[] modulePropertyLoad(Properties moduleProperties, ClassLoader classLoader){
        String key = "load.bean";
        String loadBeans = moduleProperties.getProperty(key);
        if(loadBeans == null){
            return new Class[0];
        }else if(loadBeans.trim().length() == 0){
            return new Class[0];
        }else{
            return Arrays.stream(loadBeans.split(","))
                    .map(String::trim)
                    .filter(c -> c.length() > 0)
                    .map(c -> {
                        try {
                            return classLoader.loadClass(c);
                        } catch (ClassNotFoundException e) {
                            throw new ModuleException("load", e, c);
                        }
                    }).peek(c -> QQLog.debug("resources.module.load.bean", c)).toArray(Class[]::new);
        }
    }


}
