package com.forte.qqrobot.anno;

/**
 * 备用注解，用于开发者来标注版本号等信息，暂未使用
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Version {
    String version();
    String author();
    String comment();
}
