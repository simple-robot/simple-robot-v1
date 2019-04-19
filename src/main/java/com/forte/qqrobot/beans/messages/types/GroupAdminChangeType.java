package com.forte.qqrobot.beans.messages.types;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 11:36
 * @since JDK1.8
 **/
public enum GroupAdminChangeType {

    /** 成为管理员 */
    BECOME_ADMIN(1),
    /** 被取消管理员 */
    CANCEL_ADMIN(-1);


    private final int TYPE;

    GroupAdminChangeType(int type){
        this.TYPE = type;
    }

}
