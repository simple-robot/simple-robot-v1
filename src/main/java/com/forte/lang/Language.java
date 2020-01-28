package com.forte.lang;

import sun.util.locale.BaseLocale;

import java.util.Locale;

/**
 *
 * 语言类，用于读取lang文件
 * lang文件本质上也是一种properties文件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Language {

    private static final Locale DEFAULT_LOCALE = Locale.US;

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
    public static void main(String[] args) {
//        System.getProperties().forEach((k, v) -> System.out.println(k + "\t=\t" + v));

        String language = System.getProperties().getProperty("user.language");
        String country = System.getProperties().getProperty("user.country");


        String languageTag = language + "_" + country;

        Locale def = Locale.getDefault();
        System.out.println(def);

        System.out.println(def.toString());

        System.out.println(Locale.US.getDisplayName());

    }
}
