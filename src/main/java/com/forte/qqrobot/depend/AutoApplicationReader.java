package com.forte.qqrobot.depend;

import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.utils.FieldUtils;
import com.forte.qqrobot.utils.ReaderProperties;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 自动装配配置读取器，静态方法即可。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class AutoApplicationReader {

    /**
     * 读取最前面的application.factory文件，并转化为Class
     */
    public static BaseApplication readApplicationFirst(ClassLoader loader) throws IOException {
        String resourcePath = "simbot/factory/application.factory";
        String key = "load.application";
        Enumeration<URL> resources = loader.getResources(resourcePath);

        String className = null;

        while (resources.hasMoreElements()) {
            URL next = resources.nextElement();
            Properties p = new ReaderProperties();
            p.load(next.openStream());
            String propertyApplicationName = p.getProperty(key);
            if (propertyApplicationName != null && propertyApplicationName.trim().length() > 0) {
                className = propertyApplicationName;
                break;
            }
        }


        if (className == null) {
            throw new RobotRuntimeException(1, "can not found any auto application factory file '" + resourcePath + "' in classPath");
        }

        Class<?> clazz = null;

        // contains
        try {
            clazz = loader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RobotRuntimeException(1, "class '" + className + "' not found.", e);
        }

        // 判断clazz是否为BaseApplication的子类
        boolean child = FieldUtils.isChild(clazz, BaseApplication.class);
        if (!child) {
            // 不是，抛出异常
            throw new RobotRuntimeException(1, "class '"+ clazz +"' does not extends BaseApplication.");
        } else {
            try {
                return (BaseApplication) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RobotRuntimeException(e);
            }
        }


    }


}
