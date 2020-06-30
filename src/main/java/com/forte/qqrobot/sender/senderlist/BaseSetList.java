/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BaseSetList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.exception.RobotApiException;

/**
 * @see BaseRootSenderList
 */
@Deprecated
public abstract class BaseSetList implements SenderSetList {

    /**
     * 好友请求申请
     *
     * @param flag       一般会有个标识
     * @param friendName 如果通过，则此参数为好友备注
     * @param agree      是否通过
     */
    @Override
    public boolean setFriendAddRequest(String flag, String friendName, boolean agree) {
        throw RobotApiException.byFrom();
    }

    /**
     * 群添加申请
     *
     * @param flag        一般会有个标识
     * @param requestType 加群类型  邀请/普通添加
     * @param agree       是否同意
     * @param why         如果拒绝，则此处为拒绝理由
     */
    @Override
    public boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置群管理员
     *
     * @param group 群号
     * @param QQ    qq号
     * @param set   是否设置为管理员
     */
    @Override
    public boolean setGroupAdmin(String group, String QQ, boolean set) {
        throw RobotApiException.byFrom();
    }

    /**
     * 是否允许群匿名聊天
     *
     * @param group 群号
     * @param agree 是否允许
     */
    @Override
    public boolean setGroupAnonymous(String group, boolean agree) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置匿名成员禁言
     *
     * @param group 群号
     * @param flag  匿名成员标识
     * @param time  时长，一般是以分钟为单位
     */
    @Override
    public boolean setGroupAnonymousBan(String group, String flag, long time) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置群禁言
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param time  时长，一般是以分钟为单位
     */
    @Override
    public boolean setGroupBan(String group, String QQ, long time) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置群成员名片
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param card  名片
     */
    @Override
    public boolean setGroupCard(String group, String QQ, String card) {
        throw RobotApiException.byFrom();
    }

    /**
     * 删除群文件<br>
     * ! 此接口可能不成熟
     *
     * @param group 群号
     * @param flag  一般应该会有个标识
     */
    @Override
    public boolean setGroupFileDelete(String group, String flag) {
        throw RobotApiException.byFrom();
    }

    /**
     * 退出讨论组
     *
     * @param group 讨论组号
     */
    @Override
    public boolean setDiscussLeave(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 退出群
     *
     * @param group 群号
     */
    @Override
    public boolean setGroupLeave(String group, boolean d) {
        throw RobotApiException.byFrom();
    }

    /**
     * 踢出群成员
     *
     * @param group    群号
     * @param QQ       QQ号
     * @param dontBack 是否拒绝再次申请
     */
    @Override
    public boolean setGroupMemberKick(String group, String QQ, boolean dontBack) {
        throw RobotApiException.byFrom();
    }

    /**
     * 群签到
     *
     * @param group 群号
     */
    @Override
    public boolean setGroupSign(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置群成员专属头衔
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     * @param time  有效时长，一般为分钟吧
     */
    @Override
    public boolean setGroupExclusiveTitle(String group, String QQ, String title, long time) {
        throw RobotApiException.byFrom();
    }

    /**
     * 设置全群禁言
     *
     * @param group 群号
     * @param in    是否开启全群禁言
     */
    @Override
    public boolean setGroupWholeBan(String group, boolean in) {
        throw RobotApiException.byFrom();
    }

    /**
     * 消息撤回 似乎只需要一个消息ID即可
     * 需要pro
     *
     * @param flag 消息标识
     */
    @Override
    public boolean setMsgRecall(String flag) {
        throw RobotApiException.byFrom();
    }

    /**
     * 打卡
     */
    @Override
    public boolean setSign() {
        throw RobotApiException.byFrom();
    }
}
