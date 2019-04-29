package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.types.GroupAdminChangeType;

/**
 * 群管理员变动事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/18 11:35
 * @since JDK1.8
 **/
public interface GroupAdminChange extends EventGet {

    /** 来自的群 */
    String getGroup();

    /** 操作者的QQ号 */
    String getOperatorQQ();

    /** 被操作者的QQ号 */
    String getBeOperatedQQ();

    /** 获取管理员变动类型 */
    GroupAdminChangeType getType();


}
