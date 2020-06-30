/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GetterUpper.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.get.*;
import com.forte.qqrobot.beans.messages.result.*;

/**
 * Getter系列方法增强，为方法提供封装类作为参数的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetterUpper extends Getter {


    /**
     * 取匿名成员信息
     * 一般是使用匿名标识来获取
     */
    default AnonInfo getAnonInfo(GetAnonInfo get){
        return getAnonInfo(get.getFlag());
    }

//    /**
//     * 获取权限信息
//     * 一般不需要参数
//     * @return 权限信息
//     */
//    AuthInfo getAuthInfo();

    /**
     * 获取封禁成员列表
     */
    default BanList getBanList(GetBanList get){
        return getBanList(get.getGroup());
    }

    /**
     * 获取群文件信息
     */
    default FileInfo getFileInfo(GetFileInfo get){
        return getFileInfo(get.getFlag());
    }

//    /**
//     * 获取好友列表
//     * @return 好友列表
//     */
//    FriendList getFriendList();

    /**
     * 取群作业列表
     */
    default GroupHomeworkList getGroupHomeworkList(GetGroupHomeworkList get){
        return getGroupHomeworkList(get.getGroup());
    }

    /**
     * 取群信息
     */
    default GroupInfo getGroupInfo(GetGroupInfo get){
        return getGroupInfo(get.getGroup());
    }

    /**
     * 取群连接列表
     */
    default GroupLinkList getGroupLinkList(GetGroupLinkList get){
        return getGroupLinkList(get.getGroup());
    }

//    /**
//     * 取群列表
//     * @return 群列表
//     */
//    GroupList getGroupList();

    /**
     * 取群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(GetGroupMemberInfo get){
        return getGroupMemberInfo(get.getGroup(), get.getQQ());
    }

    /**
     * 取群成员列表
     */
    default GroupMemberList getGroupMemberList(GetGroupMemberList get){
        return getGroupMemberList(get.getGroup());
    }

    /**
     * 取群公告列表
     */
    default GroupNoteList getGroupNoteList(GetGroupNoteList get){
        return getGroupNoteList(get.getGroup());
    }

    /**
     * 取置顶群公告
     */
    default GroupTopNote getGroupTopNote(GetGroupTopNote get){
        return getGroupTopNote(get.getGroup());
    }

    /**
     * 获取图片信息
     */
    default ImageInfo getImageInfo(GetImageInfo get){
        return getImageInfo(get.getFlag());
    }

//    /**
//     * 获取登录的QQ的信息
//     * @return 登录QQ的信息
//     */
//    LoginQQInfo getLoginQQInfo();

    /**
     * 获取群共享文件列表
     */
    default ShareList getShareList(GetShareList get){
        return getShareList(get.getGroup());
    }

    /**
     * 取陌生人信息
     */
    default StrangerInfo getStrangerInfo(GetStrangerInfo get){
        return getStrangerInfo(get.getQQ());
    }


}
