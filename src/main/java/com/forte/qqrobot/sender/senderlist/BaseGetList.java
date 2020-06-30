/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BaseGetList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.exception.RobotApiException;

/**
 * @see BaseRootSenderList
 **/
@Deprecated
public abstract class BaseGetList implements SenderGetList {

    /**
     * 取匿名成员信息
     * 一般是使用匿名标识来获取
     *
     * @param flag
     * @return 匿名成员信息
     */
    @Override
    public AnonInfo getAnonInfo(String flag) {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取权限信息
     * 一般不需要参数
     *
     * @return 权限信息
     */
    @Override
    public AuthInfo getAuthInfo() {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取封禁成员列表
     *
     * @param group 群号
     * @return 封禁列表
     */
    @Override
    public BanList getBanList(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取群文件信息
     *
     * @param flag 文件标识
     * @return 群文件信息
     */
    @Override
    public FileInfo getFileInfo(String flag) {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    @Override
    public FriendList getFriendList() {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群作业列表
     *
     * @param group  群号
     * @param number 获取数量
     * @return 群作业列表
     */
    @Override
    public GroupHomeworkList getGroupHomeworkList(String group, int number) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群信息
     *
     * @param group 群号
     * @param cache 是否使用缓存
     * @return 群信息
     */
    @Override
    public GroupInfo getGroupInfo(String group, boolean cache) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群链接列表
     *
     * @param group  群号
     * @param number 获取数量
     * @return 群链接
     */
    @Override
    public GroupLinkList getGroupLinkList(String group, int number) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群列表
     *
     * @return 群列表
     */
    @Override
    public GroupList getGroupList() {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群成员信息
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否使用缓存
     * @return 群成员信息
     */
    @Override
    public GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群成员列表
     *
     * @param group 群号
     * @return 成员列表
     */
    @Override
    public GroupMemberList getGroupMemberList(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取群公告列表
     *
     * @param group  群号
     * @param number 数量
     * @return 群公告列表
     */
    @Override
    public GroupNoteList getGroupNoteList(String group, int number) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取置顶群公告
     *
     * @param group 群号
     * @return 置顶群公告
     */
    @Override
    public GroupTopNote getGroupTopNote(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取图片信息
     *
     * @param flag 图片文件名或标识
     * @return 图片信息
     */
    @Override
    public ImageInfo getImageInfo(String flag) {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取登录的QQ的信息
     *
     * @return 登录QQ的信息
     */
    @Override
    public LoginQQInfo getLoginQQInfo() {
        throw RobotApiException.byFrom();
    }

    /**
     * 获取群共享文件列表
     *
     * @param group
     * @return 共享文件列表
     */
    @Override
    public ShareList getShareList(String group) {
        throw RobotApiException.byFrom();
    }

    /**
     * 取陌生人信息
     *
     * @param QQ    陌生人的QQ号
     * @param cache 是否使用缓存
     * @return
     */
    @Override
    public StrangerInfo getStrangerInfo(String QQ, boolean cache) {
        throw RobotApiException.byFrom();
    }
}
