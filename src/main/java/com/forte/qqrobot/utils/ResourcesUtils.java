package com.forte.qqrobot.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.ClassLoaderUtil;
import com.forte.qqrobot.exception.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 可以提供类加载器的资源获取工具
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ResourcesUtils {

    /**
     * classloader如果为null则使用当前线程的classLoader
     * 如果path存在对应的相对路径文件，则优先使用文件
     * @param path 文件或资源路径
     * @param loader loader
     * @return 资源
     */
    public static Resource getResource(String path, ClassLoader loader){
        if(loader == null){
            loader = ClassLoaderUtil.getClassLoader();
        }
        Resource resource;
        File file = FileUtil.file(path);
        if(file.exists()){
            resource = new FileResource(file);
        }else{
            resource = new ClassPathResource(path, loader);
        }
        return resource;
    }

    /**
     * 获取资源列表
     * @param path   path资源路径
     * @param loader 类加载器，如果为null则获取当前线程的类加载器
     */
    public static List<URL> getResources(String path, ClassLoader loader){
        final Enumeration<URL> resources;
        if(loader == null){
            loader = ClassLoaderUtil.getClassLoader();
        }
        try {
            resources = loader.getResources(path);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return CollUtil.newArrayList(resources);
    }

    /**
     * 从properties中读取对应的active内容，追加到指定的properties中
     * 覆盖追加
     * @param baseResourceName 主配置资源文件
     * @param properties 此配置文件的properties信息
     */
    public static void resourceActive(String baseResourceName, String[] actives, Properties properties, ClassLoader loader){
        if(loader == null){
            loader = ClassLoaderUtil.getClassLoader();
        }
        final int lastIndexOf = baseResourceName.lastIndexOf(".");
        String[] activeResourceNames;
        if(lastIndexOf > 0){
            final String prefix = baseResourceName.substring(0, lastIndexOf);
            final String suffix = baseResourceName.substring(lastIndexOf);
            activeResourceNames = Arrays.stream(actives).map(ac -> prefix + "-" + ac + suffix).toArray(String[]::new);
        }else{
            activeResourceNames = Arrays.stream(actives).map(ac -> baseResourceName + "-" + ac).toArray(String[]::new);
        }

        for (String activeResourceName : activeResourceNames) {
            final Resource resource = ResourcesUtils.getResource(activeResourceName, loader);
            try {
                final InputStream resourceStream = resource.getStream();
                properties.load(resourceStream);
            }catch (NoResourceException | IOException e){
                throw new ConfigurationException("can not load active config \"" + activeResourceName + "\": " + e.getLocalizedMessage(), e);
            }
        }

    }

}
