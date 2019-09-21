package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.FriendAddRequest;
import com.forte.qqrobot.beans.messages.msgget.GroupAddRequest;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;

/**
 * set相关方法接口
 * 1.2.1增加扩充方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderSetList extends SenderList {

    /**
     * 好友请求申请
     * @param flag  一般会有个标识
     * @param friendName    如果通过，则此参数为好友备注
     * @param agree 是否通过
     */
    boolean setFriendAddRequest(String flag, String friendName, boolean agree);

    /**
     * 同意好友申请
     * @param flag  唯一标识
     * @param friendName 备注
     */
    default boolean setFriendAddRequestAgree(String flag, String friendName){
        return setFriendAddRequest(flag, friendName, true);
    }

    /**
     * 拒绝好友申请
     * @param flag  唯一标识
     */
    default boolean setFriendAddRequestDisagree(String flag){
        return setFriendAddRequest(flag, "", false);
    }

    /**
     * 同意好友申请
     * @param request  事件类型对象
     * @param friendName 备注
     */
    default boolean setFriendAddRequestAgree(FriendAddRequest request, String friendName){
        return setFriendAddRequest(request, friendName, true);
    }

    /**
     * 拒绝好友申请
     * @param request  事件类型
     */
    default boolean setFriendAddRequestDisagree(FriendAddRequest request){
        return setFriendAddRequest(request, "", false);
    }

    /**
     * 好友请求申请
     * @param request       事件类型对象
     * @param friendName    如果通过，则此参数为好友备注
     * @param agree         是否通过
     */
    default boolean setFriendAddRequest(FriendAddRequest request, String friendName, boolean agree){
        return setFriendAddRequest(request.getFlag(), friendName, agree);
    }

    /**
     * 群添加申请
     * @param flag  一般会有个标识
     * @param requestType   加群类型  邀请/普通添加
     * @param agree 是否同意
     * @param why   如果拒绝，则此处为拒绝理由
     */
    boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why);

    /**
     * 同意群添加申请
     * @param flag  唯一标识
     * @param requestType   加群类型  邀请/普通添加
     */
    default boolean setGroupAddRequestAgree(String flag, GroupAddRequestType requestType){
        return setGroupAddRequest(flag, requestType, true, "");
    }

    /**
     * 拒绝群添加申请
     * @param flag  唯一标识
     * @param requestType   加群类型  邀请/普通添加
     */
    default boolean setGroupAddRequestDisagree(String flag, GroupAddRequestType requestType, String why){
        return setGroupAddRequest(flag, requestType, false, why);
    }

    /**
     * 同意群添加申请
     * @param request  群添加事件对象
     */
    default boolean setGroupAddRequestAgree(GroupAddRequest request){
        return setGroupAddRequest(request, true, "");
    }

    /**
     * 拒绝群添加申请
     * @param request  群添加事件对象
     */
    default boolean setGroupAddRequestDisagree(GroupAddRequest request, String why){
        return setGroupAddRequest(request, false, why);
    }

    /**
     * 群添加申请
     * @param request  群添加事件对象
     * @param agree     是否同意
     * @param why       如果拒绝，则此处为拒绝理由
     */
    default boolean setGroupAddRequest(GroupAddRequest request, boolean agree, String why){
        return setGroupAddRequest(request.getFlag(), request.getRequestType(), agree, why);
    }

    /**
     * 设置群管理员
     * @param group 群号
     * @param QQ    qq号
     * @param set   是否设置为管理员
     */
    boolean setGroupAdmin(String group, String QQ, boolean set);



    /**
     * 设置群管理员
     * @param group 群号携带者
     * @param qq    qq号携带者
     * @param set   是否设置为管理员
     */
    default boolean setGroupAdmin(GroupCodeAble group, QQCodeAble qq, boolean set){
        return setGroupAdmin(group.getGroupCode(), qq.getQQCode(), set);
    }

    /**
     * 设置群管理员
     * @param codes 群号携带者与qq号携带者
     * @param set   是否设置为管理员
     */
    default boolean setGroupAdmin(CodesAble codes, boolean set){
        return setGroupAdmin(codes.getGroupCode(), codes.getQQCode(), set);
    }

    /**
     * 是否允许群匿名聊天
     * @param group 群号
     * @param agree 是否允许
     */
    boolean setGroupAnonymous(String group, boolean agree);

    /**
     * 是否允许群匿名聊天
     * @param group 群号携带者
     * @param agree 是否允许
     */
    default boolean setGroupAnonymous(GroupCodeAble group, boolean agree){
        return setGroupAnonymous(group.getGroupCode(), agree);
    }

    /**
     * 设置匿名成员禁言
     * @param group 群号
     * @param flag  匿名成员标识
     * @param time  时长，一般是以分钟为单位
     */
    boolean setGroupAnonymousBan(String group, String flag, long time);

    /**
     * 设置群禁言
     * @param group 群号
     * @param QQ    QQ号
     * @param time  时长，一般是以秒为单位
     */
    boolean setGroupBan(String group, String QQ, long time);

    /**
     * 设置群禁言
     * @param group 群号携带者
     * @param QQ    QQ号携带者
     * @param time  时长，一般是以秒为单位
     */
    default boolean setGroupBan(GroupCodeAble group, QQCodeAble QQ, long time){
        return setGroupBan(group.getGroupCode(), QQ.getQQCode(), time);
    }

    /**
     * 设置群禁言
     * @param codes 群号携带者与QQ号携带者
     * @param time  时长，一般是以秒为单位
     */
    default boolean setGroupBan(CodesAble codes, long time){
        return setGroupBan(codes.getGroupCode(), codes.getQQCode(), time);
    }

    /**
     * 设置群成员名片
     * @param group 群号
     * @param QQ    QQ号
     * @param card  名片
     */
    boolean setGroupCard(String group, String QQ, String card);

    /**
     * 设置群成员名片
     * @param group 群号
     * @param QQ    QQ号
     * @param card  名片
     */
    default boolean setGroupCard(GroupCodeAble group, QQCodeAble QQ, String card){
        return setGroupCard(group.getGroupCode(), QQ.getQQCode(), card);
    }

    /**
     * 设置群成员名片
     * @param codes 群号携带者与QQ号携带者
     * @param card  名片
     */
    default boolean setGroupCard(CodesAble codes, String card){
        return setGroupCard(codes.getGroupCode(), codes.getQQCode(), card);
    }

    /**
     * 删除群文件<br>
     *     ! 此接口可能不成熟
     * @param group 群号
     * @param flag  一般应该会有个标识
     */
    boolean setGroupFileDelete(String group, String flag);

    /**
     * 删除群文件
     * @param group 群号携带者
     * @param flag  标识
     */
    default boolean setGroupFileDelete(GroupCodeAble group, String flag){
        return setGroupFileDelete(group.getGroupCode(), flag);
    }

    /**
     * 退出讨论组
     * @param group 讨论组号
     */
    boolean setDiscussLeave(String group);

    /**
     * 退出讨论组
     * @param group 讨论组号
     */
    default boolean setDiscussLeave(GroupCodeAble group){
        return setDiscussLeave(group.getGroupCode());
    }

    /**
     * 退出群
     * @param group 群号
     */
    boolean setGroupLeave(String group);

    /**
     * 退出群
     * @param group 群号
     */
    default boolean setGroupLeave(GroupCodeAble group){
        return setGroupLeave(group.getGroupCode());
    }

    /**
     * 踢出群成员
     * @param group 群号
     * @param QQ    QQ号
     * @param dontBack  是否拒绝再次申请
     */
    boolean setGroupMemberKick(String group, String QQ, boolean dontBack);

    /**
     * 踢出群成员
     * @param group 群号
     * @param QQ    QQ号
     * @param dontBack  是否拒绝再次申请
     */
    default boolean setGroupMemberKick(GroupCodeAble group, QQCodeAble QQ, boolean dontBack){
        return setGroupMemberKick(group.getGroupCode(), QQ.getQQCode(), dontBack);
    }

    /**
     * 踢出群成员
     * @param codes 群号,QQ号 携带者
     * @param dontBack  是否拒绝再次申请
     */
    default boolean setGroupMemberKick(CodesAble codes, boolean dontBack){
        return setGroupMemberKick(codes.getGroupCode(), codes.getQQCode(), dontBack);
    }


    /**
     * 群签到
     * @param group 群号
     */
    boolean setGroupSign(String group);


    /**
     * 群签到
     * @param group 群号
     */
    default boolean setGroupSign(GroupCodeAble group){
        return setGroupSign(group.getGroupCode());
    }

    /**
     * 设置群成员专属头衔
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     * @param time  有效时长，一般为分钟吧
     */
    boolean setGroupExclusiveTitle(String group, String QQ, String title, long time);


    /**
     * 设置群成员专属头衔
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     * @param time  有效时长，一般为分钟吧
     */
    default boolean setGroupExclusiveTitle(GroupCodeAble group, QQCodeAble QQ, String title, long time){
        return setGroupExclusiveTitle(group.getGroupCode(), QQ.getQQCode(), title, time);
    }


    /**
     * 设置群成员专属头衔
     * @param codes 群号, QQ号 携带者
     * @param title 头衔
     * @param time  有效时长，一般为分钟吧
     */
    default boolean setGroupExclusiveTitle(CodesAble codes, String title, long time){
        return setGroupExclusiveTitle(codes.getGroupCode(), codes.getQQCode(), title, time);
    }

    /**
     * 设置全群禁言
     * @param group 群号
     * @param in    是否开启全群禁言
     */
    boolean setGroupWholeBan(String group, boolean in);

    /**
     * 设置全群禁言
     * @param group 群号
     * @param in    是否开启全群禁言
     */
    default boolean setGroupWholeBan(GroupCodeAble group, boolean in){
        return setGroupWholeBan(group.getGroupCode(), in);
    }

    /**
     * 消息撤回 似乎只需要一个消息ID即可
     * 需要pro
     * @param flag  消息标识
     */
    boolean setMsgRecall(String flag);

    /**
     * 消息撤回 似乎只需要一个消息ID即可
     * 需要pro
     * @param flag  消息标识
     */
    default boolean setMsgRecall(FlagAble flag){
        return setMsgRecall(flag.getFlag());
    }

    /**
     * 打卡
     */
    boolean setSign();


}
