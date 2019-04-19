package com.forte.qqrobot.beans.messages.get;


import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;

/**
 * 获取群成员信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetGroupMemberInfo extends InfoGet<GroupMemberInfo> {

    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @Override
    default Class<GroupMemberInfo> resultType(){
        return GroupMemberInfo.class;
    }

}
