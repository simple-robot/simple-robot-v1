package com.forte.qqrobot.beans.messages.result;

/**
 * 权限信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface AuthInfo extends InfoResult {

    /**
     * 获取一个编码
     */
    String getCode();

    /**
     * 获取cookies信息
     */
    String getCookies();

    /**
     * 获取CsrfToken
     */
    String getCsrfToken();



}
