package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;

/**
 * set相关方法接口
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderSetList extends SenderList {

    /**
     * 退出讨论组
     * @param group 讨论组号
     */
    boolean setDiscussLeave(String group);

    /**
     * 好友请求申请
     * @param flag  一般会有个标识
     * @param friendName    如果通过，则此参数为好友备注
     * @param agree 是否通过
     */
    boolean setFriendAddRequest(String flag, String friendName, boolean agree);

    /**
     * 群添加申请
     * @param flag  一般会有个标识
     * @param requestType   加群类型  邀请/普通添加
     * @param agree 是否同意
     * @param why   如果拒绝，则此处为拒绝理由
     */
    boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why);

    /**
     * 设置群管理员
     * @param group 群号
     * @param QQ    qq号
     * @param set   是否设置为管理员
     */
    boolean setGroupAdmin(String group, String QQ, boolean set);

    /**
     * 是否允许群匿名聊天
     * @param group 群号
     * @param agree 是否允许
     */
    boolean setGroupAnonymous(String group, boolean agree);

    /**
     * 设置匿名成员禁言
     * @param group 群号
     * @param flag  匿名成员标识
     * @param time  时长，一般是以分钟为单位
     */
    boolean setGroupAnonymousBan(String group, String flag, Long time);

    /**
     * 设置群禁言
     * @param group 群号
     * @param QQ    QQ号
     * @param time  时长，一般是以分钟为单位
     */
    boolean setGroupBan(String group, String QQ, Long time);

    /**
     * 设置群成员名片
     * @param group 群号
     * @param QQ    QQ号
     * @param card  名片
     */
    boolean setGroupCard(String group, String QQ, String card);

    /**
     * 删除群文件<br>
     *     ! 此接口可能不成熟
     * @param group 群号
     * @param flag  一般应该会有个标识
     */
    boolean setGroupFileDelete(String group, String flag);

    /**
     * 退出群
     * @param group 群号
     */
    boolean setGroupLeave(String group);

    /**
     * 踢出群成员
     * @param group 群号
     * @param QQ    QQ号
     * @param dontBack  是否拒绝再次申请
     */
    boolean setGroupMemberKcik(String group, String QQ, boolean dontBack);

    /**
     * 群签到
     * @param group 群号
     */
    boolean setGroupSign(String group);

    /**
     * 设置群成员专属头衔
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     * @param time  有效时长，一般为分钟吧
     */
    boolean setGroupExclusiveTitle(String group, String QQ, String title, Long time);

    /**
     * 设置全群禁言
     * @param group 群号
     * @param in    是否开启全群禁言
     */
    boolean setGroupWholeBan(String group, boolean in);

    /**
     * 似乎只需要一个消息ID即可
     * 需要pro
     * @param flag  消息标识
     */
    boolean setMsgRecall(String flag);

    /**
     * 打卡
     */
    boolean setSign();




}
