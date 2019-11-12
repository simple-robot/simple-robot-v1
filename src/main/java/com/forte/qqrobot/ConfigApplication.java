package com.forte.qqrobot;

/**
 *
 * 允许使用配置文件的接口。
 *
 * 目前暂时仅支持properties（因为简单且不需要其他依赖
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ConfigApplication<CONFIG extends BaseConfiguration> extends Application<CONFIG> {
}
