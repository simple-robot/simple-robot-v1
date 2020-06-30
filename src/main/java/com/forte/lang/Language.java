/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Language.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.lang;


import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.Resource;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.ReaderProperties;
import com.forte.qqrobot.utils.ResourcesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 语言类，用于读取lang文件
 * lang文件本质上也是一种properties文件
 *
 * 其本质为MessageFormat进行格式化，所以请注意MessageFormat中使用的规则，例如单引号为类似于转义符的存在之类的
 *
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Language {

    /**
     * 核心使用语言文件所在路径
     */
    private static final String CORE_PATH_HEAD = "lang/core/";
    /**
     * 组件使用的语言文件所在路径
     */
    private static final String COMPONENT_PATH_HEAD = "lang/component/";

    /**
     * modules下的文件，modules可能会有多个
     */
    private static final String MODULES_PATH_HEAD = "lang/modules/";

    /**
     * 语言文件路径下的前缀
     */
    private static final String LANG_PATH_HEAD = "lang/";

    /**
     * lang文件扩展名
     */
    private static final String PATH_END = ".lang";

    /**
     * 编码使用默认编码
     */
    private static Charset charset = StandardCharsets.UTF_8;

    /**
     * 默认的语言使用英文
     */
    private static final Locale DEFAULT_LOCALE = Locale.US;

    /**
     * 当语言被忽略的时候，使用的默认语言，为系统当前默认语言
     */
    private static final Locale SYSTEM_DEFAULT_LOCALE = Locale.getDefault();

    /**
     * 语言格式化
     */
    private static Map<String, MessageFormat> languageFormat;

    /**
     * 是否已经初始化了
     */
    private static boolean ready = false;


    /**
     * 是否初始化完毕。
     */
    public static boolean already() {
        return ready;
    }


    private static final String LANG_INIT_FINISHED = "lang.init.finished";

    /*
        sun.desktop	=	windows
        os.name	=	Windows 10
        os.version	=	10.0
        sun.cpu.isalist	=	amd64
        os.arch	=	amd64

        user.language	=	zh
        user.country	=	CN


        file.separator	=	\

        file.encoding	=	UTF-8
        sun.jnu.encoding	=	GBK

     */

    /**
     * 根据语言坐标获取国际化翻译
     *
     * @param langTag   语言坐标，例如error.info.e1，其代表lang文件中的key值
     * @param formatTag 格式化坐标，当langTag能够获取到结果的时候，此formatTag则会作为其格式化参数。例如:
     *                  <br>
     *                  <code>
     *                  // text.msg=hhh{0} <br>
     *                  String msg = format("text.msg", 233);  <br>
     *                  // msg -> "hhh233"  <br>
     *                  </code>
     *                  <br>
     *                  其格式化使用的是{@link MessageFormat}
     * @return 格式化后的语言
     * 假如没有找到对应的tag，则会直接将此tag格式化并返回
     */
    public static String format(String langTag, Object... formatTag) {
        if (!ready || languageFormat == null) {
            return MessageFormat.format(langTag, formatTag);
        }

        MessageFormat langTagFormat = languageFormat.get(langTag);
        if (langTagFormat == null) {
            // 不存在此tag, 直接返回此tag的格式化结果
            return MessageFormat.format(langTag, formatTag);
        } else {
            // 格式化返回
            return langTagFormat.format(formatTag);
        }
    }

    /**
     * 代替部分字符串首位拼接的方法
     *
     * 至于效率问题，测试数据将会放在这里, 单位ms
     * <p>第一轮</p>
     * <code>
     *         <p>1000000 times</p>
     *         <p>两个同时比较 plus 在前</p>
     *         <p>plus 3366 2681 2745 2741 2707</p>
     *         <p>char 1975 2156 2179 2224 2159</p>
     *         <p>两个同时比较 char 在前</p>
     *         <p>plus 2008 2085 2097 2065 2094</p>
     *         <p>char 3017 2750 2788 2831 2771</p>
     *         <p>单独跑</p>
     *         <p>plus 2670 2680 2679 3270 2732</p>
     *         <p>char 2705 2954 2771 2881 2789</p>
     * </code>
     * <p>第二轮</p>
     * <code>
     *      <p>1000000 times</p>
     *      <p>一起</p>
     *      <p>char 在前</p>
     *      <p>char 2780 2733 2920</p>
     *      <p>plus 2159 2062 2052</p>
     *      <p>plus 在前</p>
     *      <p>char 2137 2122 2171</p>
     *      <p>plus 2721 2689 2683</p>
     *      <p>单独</p>
     *      <p>char 2681 2724 2778</p>
     *      <p>plus 2769 2697 2675</p>
     * </code>
     *
     * @param head  tag头部
     * @param tag   tag本体
     * @param formatTag 格式化字符串
     * @return
     */
    public static String format(String head, String tag, Object... formatTag){
        int headSize = head.length();
        int tagSize = tag.length();
        char[] chars = new char[headSize + tagSize + 1];
        chars[headSize] = '.';
        head.getChars(0, headSize, chars, 0);
        tag.getChars(0, tagSize, chars, headSize + 1);

        return format(new String(chars), formatTag);
    }



    /**
     * 通过tagName结果获取一个Locale对象。
     * 例如："en_US" -> Locale.US
     *
     * @param tagName tagName
     * @return
     */
    public static Locale getLocaleByTag(String tagName) {
        return Locale.forLanguageTag(tagName.replace('_', '-'));
    }


    /**
     * 自动获取系统时区的初始化
     *
     * @param loader 类加载器
     * @see #init(ClassLoader, Locale)
     */
    public static void init(ClassLoader loader) {
        init(loader, SYSTEM_DEFAULT_LOCALE);
    }

    /**
     * 自动获取系统时区的初始化
     *
     * @param locale 加载的语言
     * @see #init(ClassLoader, Locale)
     */
    public static void init(Locale locale) {
        init(ClassLoader.getSystemClassLoader(), locale);
    }

    /**
     * 自动获取系统时区的初始化
     * 使用系统默认的类加载器，一般来讲就是AppClassLoader
     * @see #init(ClassLoader)
     * @see #init(ClassLoader, Locale)
     */
    public static void init() {
        init(ClassLoader.getSystemClassLoader(), SYSTEM_DEFAULT_LOCALE);
    }

    /**
     * 初始化语言，给一个类加载器
     *
     * @param classLoader 类加载器，从此加载器加载resources文件
     * @param locale      当前使用的语言区域
     */
    public static void init(ClassLoader classLoader, Locale locale) {
        // 初始化map
        languageFormat = new HashMap<>(16);
        // 首先初始化默认语言
        try {
            Exception[] exceptions = defaultInit(classLoader);
            if(exceptions.length > 0){
                QQLog.warning("default language init some failed. You can still use it, but some language may be missing.");
                QQLog.debug("default language init some failed. You can still use it, but some language may be missing.");
                for (int i = 0; i < exceptions.length; i++) {
                    QQLog.warning("\texception {0} > {1}", i, exceptions[i].getMessage());
                    QQLog.debug("\texception {0} > ", exceptions[i], i);
                }
            }
        } catch (Exception e) {
            QQLog.error("default language ''{0}'' init failed. You can still use it, but some language may be missing.", DEFAULT_LOCALE);
            QQLog.debug("default language ''{0}'' init failed. You can still use it, but some language may be missing.", e, DEFAULT_LOCALE);

        }

        //********************************//

        // 其次初始化系统语言，假如语言与默认相同则跳过此步骤
        if (!DEFAULT_LOCALE.equals(locale)) {
            try {
                Exception[] exceptions = localeInit(classLoader, locale);
                if(exceptions.length > 0){
                    QQLog.warning("locale language load some failed. You can still use it, but some language may be missing.");
                    QQLog.debug("locale language load some failed. You can still use it, but some language may be missing.");
                    for (int i = 0; i < exceptions.length; i++) {
                        QQLog.warning("\texception {0} > {1}", i, exceptions[i].getMessage());
                        QQLog.debug("\texception {0} > ", exceptions[i], i);
                    }
                }
            } catch (Exception e) {
                QQLog.error("locale language ''{0}'' load failed. You can still use it, but some language may be missing.", locale);
                QQLog.debug("locale language ''{0}'' load failed. You can still use it, but some language may be missing.", e, locale);

            }
        }

        ready = true;
        QQLog.debug(LANG_INIT_FINISHED);
    }


    /**
     * 加载一个本地语言
     *
     * @param loader 类加载器
     * @param path   resources资源路径，开头请不要带'/'，其内部会进行尝试
     * @param locale 加载的对应语言
     * @param ifNull 如果获取不到inputStream，则执行此函数
     * @throws IOException
     */
    private static void loadLang(ClassLoader loader, String path, Locale locale, Function<String, Exception> ifNull) throws Exception {
        InputStream localeLangStream = getResourcesInputStream(loader, path, ifNull);


        // 读取语言数据, 覆盖en中已经存在的语言文件
        ReaderProperties localeLangProperties = new ReaderProperties();
        localeLangProperties.load(localeLangStream);
        // 进行格式转化
        Set<String> names = localeLangProperties.stringPropertyNames();
        // 使用传统方式遍历properties
        for (String key : names) {
            languageFormat.put(key, new MessageFormat(localeLangProperties.getProperty(key), locale));
        }
    }


    /**
     * 加载所有本地语言资源
     *
     * @param loader 类加载器
     * @param path   resources资源路径，开头请不要带'/'，其内部会进行尝试
     * @param locale 加载的对应语言
     * @param ifThrow 如果出现异常
     * @throws IOException
     */
    private static void loadLangResources(ClassLoader loader, String path, Locale locale, Consumer<Exception> ifThrow) throws Exception {
        if(ifThrow == null){
            ifThrow = e -> {};
        }
        final Stream<InputStream> resourcesInputStreams = getResourcesInputStreams(loader, path, ifThrow);
        // 读取语言数据, 覆盖en中已经存在的语言文件
        ReaderProperties localeLangProperties = new ReaderProperties();

        resourcesInputStreams.forEach(in -> {
            try {
                localeLangProperties.load(in);
            } catch (IOException ignored) { }
            // 进行格式转化
            Set<String> names = localeLangProperties.stringPropertyNames();
            // 使用传统方式遍历properties
            for (String key : names) {
                languageFormat.put(key, new MessageFormat(localeLangProperties.getProperty(key), locale));
            }
        });
    }

    /**
     * 尝试获取一个resources资源输入流
     *
     * @param loader 类加载器
     * @param path   资源路径
     * @param ifNull 如果为空，使用此函数返回一个异常对象，如果此对象不为null，则会将其抛出
     * @return 输入流，如果获取不到则会返回null（没有抛出异常的情况下
     * @throws Exception
     */
    private static InputStream getResourcesInputStream(ClassLoader loader, String path, Function<String, Exception> ifNull) throws Exception {
        Resource resource = ResourcesUtils.getResource(path, loader);

        InputStream localeLangStream = null;
        try {
            localeLangStream = resource.getStream();
        }catch (NoResourceException e){
            Exception ifNullEx = ifNull.apply(path);
            if(ifNullEx != null){
                throw ifNullEx;
            }
        }
        return localeLangStream;

        // 如果为null， 尝试在开头加个'/'
//        if (localeLangStream == null) {
//            // 尝试携带一个/路径在开头
//            String newPath = "/" + path;
//            localeLangStream = loader.getResourceAsStream(newPath);
//            // 如果语言文件流为null
//            if (localeLangStream == null) {
//                if (ifNull != null) {
//                    Exception err = ifNull.apply(path);
//                    if (err != null) {
//                        throw err;
//                    }
//                }
//                return null;
//            }
//        }
    }

    /**
     * 尝试获取resources资源输入流
     *
     * @param loader 类加载器
     * @param path   资源路径
     * @param ifThrow 如果出现异常
     * @return 输入流列表
     * @throws IOException
     */
    private static Stream<InputStream> getResourcesInputStreams(ClassLoader loader, String path, Consumer<Exception> ifThrow) {
        return ResourcesUtils.getResources(path, loader).stream().map(url -> {
            try {
                return url.openStream();
            } catch (IOException e) {
                ifThrow.accept(e);
                return null;
            }
        }).filter(Objects::nonNull);
    }


    /**
     * 加载当前系统语言
     *
     * @param loader 类加载器
     * @param locale 语言区域
     */
    private static Exception[] localeInit(ClassLoader loader, Locale locale) {
        List<Exception> exceptions = new ArrayList<>();
        try {
            // 加载核心的本地语言
            loadLang(loader, CORE_PATH_HEAD + locale + PATH_END, locale, path -> new NullPointerException("can not find language resources file in : '" + path + "'"));
        } catch (Exception e) {
            exceptions.add(e);
        }
        try {
            // 加载组件的本地语言
            loadLang(loader, COMPONENT_PATH_HEAD + locale + PATH_END, locale, path -> new NullPointerException("can not find language resources file in : '" + path + "'"));
        } catch (Exception e) {
            exceptions.add(e);
        }
        try {
            // 加载modules语言
            loadLangResources(loader, MODULES_PATH_HEAD + locale + PATH_END, locale, null);
        } catch (Exception e) {
            QQLog.debug("can not find language resources file in : ''{0}''", LANG_PATH_HEAD + locale + PATH_END);
        }
        try {
            // 加载可能存在的用户自定义语言, 不会warn异常
            loadLang(loader, LANG_PATH_HEAD + locale + PATH_END, locale, null);
//            loadLang(loader, LANG_PATH_HEAD + locale + PATH_END, locale, p -> new NullPointerException("can not find language resources file in : '"+ p +"'"));
        } catch (Exception e) {
            QQLog.debug("can not find language resources file in : ''{0}''", LANG_PATH_HEAD + locale + PATH_END);
        }
        return exceptions.toArray(new Exception[0]);
    }


    /**
     * 默认语言加载器，一般默认语言为{@link Locale#US}
     *
     * @param loader 类加载器
     */
    private static Exception[] defaultInit(ClassLoader loader) throws Exception {
        List<Exception> exceptions = new ArrayList<>();
        try {
            // 加载核心的本地语言
            loadLang(loader, CORE_PATH_HEAD + DEFAULT_LOCALE + PATH_END, DEFAULT_LOCALE, path -> new LanguageInitException("can not find language resources file in : " + path));
        } catch (Exception e) {
            exceptions.add(e);
        }
        try {
            // 加载组件的本地语言
            loadLang(loader, COMPONENT_PATH_HEAD + DEFAULT_LOCALE + PATH_END, DEFAULT_LOCALE, path -> new LanguageInitException("can not find language resources file in : " + path));
        } catch (Exception e) {
            exceptions.add(e);
        }
        try {
            // 加载modules语言
            loadLangResources(loader, MODULES_PATH_HEAD + DEFAULT_LOCALE + PATH_END, DEFAULT_LOCALE, null);
        } catch (Exception e) {
            QQLog.debug("can not find language resources file in : ''{0}''", LANG_PATH_HEAD + DEFAULT_LOCALE + PATH_END);
        }
        try {
            // 加载可能存在的用户自定义语言
            loadLang(loader, LANG_PATH_HEAD + DEFAULT_LOCALE + PATH_END, DEFAULT_LOCALE, null);
        } catch (Exception e) {
            QQLog.debug("can not find language resources file in : ''{0}''", LANG_PATH_HEAD + DEFAULT_LOCALE + PATH_END);
        }
        return exceptions.toArray(new Exception[0]);
    }

    /**
     * 获取当前全部语言信息
     */
    public static Map<String, MessageFormat> getLangMap(){
        return new HashMap<>(languageFormat);
    }

    /**
     * 获取指定tag的信息
     * @param tag 指定tag
     */
    public static MessageFormat getLang(String tag){
        return languageFormat.get(tag);
    }

    /**
     * 注册或覆盖一些额外的语言映射，其最终会覆盖语言映射
     * 需要在初始化结束后再注册，否则会抛出异常
     *
     * @param properties properties对象
     */
    public static void registerTags(Properties properties, Locale locale) {
        if (!ready) {
            throw new LanguageInitException("language has not initialization already.");
        }
        // 遍历properties
        properties.stringPropertyNames()
                .forEach(key -> {
                    languageFormat.put(key, new MessageFormat(properties.getProperty(key), locale));
                });
    }

    /**
     * 注册或覆盖一些额外的语言映射，其最终会覆盖语言映射
     * 需要在初始化结束后再注册，否则会抛出异常
     *
     * @param properties properties对象
     */
    public static void registerTags(Properties properties) {
        registerTags(properties, SYSTEM_DEFAULT_LOCALE);
    }

    /**
     * 加载一个语言文件
     *
     * @param langName 语言文件名称，其格式为resources路径下：/lang/{langName}.lang
     *                 ，也就是说，如果langName=new_zh_CN，则会获取resources路径下文件：/lang/new_zh_CN.lang
     */
    public static void registerLang(ClassLoader loader, String langName, Locale locale) throws Exception {
        String path = LANG_PATH_HEAD + langName + PATH_END;
        InputStream inputStream = getResourcesInputStream(loader, path, p -> new NullPointerException("can not register lang file '" + p + "'"));

        // 存在，加载lang文件为properties
        ReaderProperties langProperties = new ReaderProperties();
        langProperties.load(inputStream);

        // 注册/覆盖一些新的东西
        registerTags(langProperties, locale);
    }

    /**
     * 加载一个语言文件
     *
     * @param langName 语言文件名称，其格式为resources路径下：/lang/{langName}.lang
     *                 ，也就是说，如果langName=new_zh_CN，则会获取resources路径下文件：/lang/new_zh_CN.lang
     */
    public static void registerLang(ClassLoader loader, String langName) throws Exception {
        registerLang(loader, langName, SYSTEM_DEFAULT_LOCALE);
    }
}
