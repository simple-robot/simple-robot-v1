package com.forte.qqrobot.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 扫描包下路径
 * 包括本地文件和jar包文件
 *
 * @author ljb
 */
public class FileScanner {

    /**
     * 储存结果的Set集合
     */
    private Set<Class<?>> eleStrategySet = new HashSet<>();

    /**
     * 默认使用的类加载器
     */
    private ClassLoader classLoader = FileScanner.class.getClassLoader();   //默认使用的类加载器

    /**
     * 构造
     */
    public FileScanner(){
    }

    /**
     * 根据过滤规则查询
     * @param classFilter class过滤规则
     * @throws FileNotFoundException
     */
    public FileScanner find(String packageName, Predicate<Class<?>> classFilter) {
        eleStrategySet.addAll(addClass(packageName, classFilter));
        return this;
    }

    /**
     * 根据过滤规则查询, 查询全部
     * @throws FileNotFoundException
     */
    public FileScanner find(String packageName) {
        eleStrategySet.addAll(addClass(packageName, c -> true));
        return this;
    }

    /**
     * 获取包下所有实现了superStrategy的类并加入list
     * @param classFilter class过滤器
     */
    private Set<Class<?>> addClass(String packageName, Predicate<Class<?>> classFilter) {
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        //如果路径为null，抛出异常
        if(url == null){
            throw new RuntimeException("路径不存在！");
        }

        //路径字符串
        String protocol = url.getProtocol();
        //如果是文件类型，使用文件扫描
        if ("file".equals(protocol)) {
            // 本地自己可见的代码
            return findClassLocal(packageName, classFilter);
            //如果是jar包类型，使用jar包扫描
        } else if ("jar".equals(protocol)) {
            // 引用jar包的代码
            return findClassJar(packageName, classFilter);
        }
        return Collections.emptySet();
    }

    /**
     * 本地查找
     *
     * @param packName
     */
    private Set<Class<?>> findClassLocal(final String packName, final Predicate<Class<?>> classFilter) {
        Set<Class<?>> set = new HashSet<>();
        URI url;
        try {
            url = classLoader.getResource(packName.replace(".", "/")).toURI();
        } catch (URISyntaxException e1) {
            throw new RuntimeException("未找到策略资源");
        }

        File file = new File(url);
        file.listFiles(chiFile -> {
            if (chiFile.isDirectory()) {
                //如果是文件夹，递归扫描
                set.addAll(findClassLocal(packName + "." + chiFile.getName(), classFilter));
            }
            if (chiFile.getName().endsWith(".class")) {
                Class<?> clazz = null;
                try {
                    clazz = classLoader.loadClass(packName + "." + chiFile.getName().replace(".class", ""));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (clazz != null && classFilter.test(clazz)) {
                    set.add(clazz);
                }
                return true;
            }
            return false;
        });

        return set;
    }

    /**
     * jar包查找
     *
     * @param packName
     */
    private Set<Class<?>> findClassJar(final String packName, final Predicate<Class<?>> classFilter) {
        Set<Class<?>> set = new HashSet<>();
        String pathName = packName.replace(".", "/");
        JarFile jarFile;
        try {
            URL url = classLoader.getResource(pathName);

            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            jarFile = jarURLConnection.getJarFile();
        } catch (IOException e) {
            throw new RuntimeException("未找到策略资源");
        }

        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();

            if (jarEntryName.contains(pathName) && !jarEntryName.equals(pathName + "/")) {
                //递归遍历子目录
                if (jarEntry.isDirectory()) {
                    String clazzName = jarEntry.getName().replace("/", ".");
                    int endIndex = clazzName.lastIndexOf(".");
                    String prefix = null;
                    if (endIndex > 0) {
                        prefix = clazzName.substring(0, endIndex);
                    }
                    set.addAll(findClassJar(prefix, classFilter));
                }
                if (jarEntry.getName().endsWith(".class")) {
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(jarEntry.getName().replace("/", ".").replace(".class", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //判断，如果符合，添加
                    if (clazz != null && classFilter.test(clazz)) {
                        set.add(clazz);
                    }
                }
            }
        }
        return set;
    }


    public Set<Class<?>> get(){
        return this.eleStrategySet;
    }

}