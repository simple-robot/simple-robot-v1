package com.forte.qqrobot.beans.messages.result;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface LoginQQInfo extends InfoResult {

    /** 昵称 */
    String getName();
    /** QQ号 */
    String getQQ();
    /** 头像地址 */
    String getHeadUrl();
    /** 等级 */
    Integer getLevel();

}
