package com.forte.qqrobot.beans.messages.result;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupList extends InfoResultList<GroupList.Group> {

    /**
     * 群列表
     */
//    Group[] getGroupList();

    /**
     * 群列表的群信息
     */
    interface Group extends ResultInner{
        /** 群名 */
        String getName();
        /** 群号 */
        String getCode();
        /** 群头像地址 */
        String getHeadUrl();
    }
}
