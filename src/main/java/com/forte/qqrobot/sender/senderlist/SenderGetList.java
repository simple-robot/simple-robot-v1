package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.result.*;

/**
 * get相关方法列表，继承get总方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderGetList extends SenderList {

    //**************************************
    //*             get相关接口
    //**************************************

    /** 如果存在一些可以自定义获取数量的接口，默认使用此数量 */
    int NUMBER = 99;
    /** 如果存在一些可以自定义是否使用缓存的接口，默认使用此选项 */
    boolean CACHE = true;

    /**
     * 取匿名成员信息
     * 一般是使用匿名标识来获取
     * @return 匿名成员信息
     */
    AnonInfo getAnonInfo(String flag);

    /**
     * 获取权限信息
     * 一般不需要参数
     * @return 权限信息
     */
    AuthInfo getAuthInfo();

    /**
     * 获取封禁成员列表
     * @param group 群号
     * @return 封禁列表
     */
    BanList getBanList(String group);

    /**
     * 获取群文件信息
     * @param flag 文件标识
     * @return 群文件信息
     */
    FileInfo getFileInfo(String flag);

    /**
     * 获取好友列表
     * @return 好友列表
     */
    FriendList getFriendList();

    /**
     * 取群作业列表
     * @param group 群号
     * @return 群作业列表
     */
    default GroupHomeworkList getGroupHomeworkList(String group){
        return getGroupHomeworkList(group, NUMBER);
    }

    /**
     * 取群作业列表
     * @param group 群号
     * @param number 获取数量
     * @return 群作业列表
     */
    GroupHomeworkList getGroupHomeworkList(String group, int number);

    /**
     * 取群信息 使用缓存
     * @param group 群号
     * @return 群信息
     */
    default GroupInfo getGroupInfo(String group){
        return getGroupInfo(group, CACHE);
    }

    /**
     * 取群信息
     * @param group 群号
     * @param cache 是否使用缓存
     * @return 群信息
     */
    GroupInfo getGroupInfo(String group, boolean cache);

    /**
     * 取群链接列表
     * @param group 群号
     * @return  群链接
     */
    default GroupLinkList getGroupLinkList(String group){
        return getGroupLinkList(group, NUMBER);
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 获取数量
     * @return  群链接
     */
    GroupLinkList getGroupLinkList(String group, int number);

    /**
     * 取群列表
     * @return 群列表
     */
    GroupList getGroupList();

    /**
     * 取群成员信息 使用缓存
     * @param group 群号
     * @param QQ    QQ号
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(String group, String QQ){
        return getGroupMemberInfo(group, QQ, CACHE);
    }

    /**
     * 取群成员信息
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否使用缓存
     * @return 群成员信息
     */
    GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache);

    /**
     * 取群成员列表
     * @param group 群号
     * @return  成员列表
     */
    GroupMemberList getGroupMemberList(String group);

    /**
     * 取群公告列表
     * @param group 群号
     * @return  群公告列表
     */
    default GroupNoteList getGroupNoteList(String group){
        return getGroupNoteList(group, NUMBER);
    }

    /**
     * 取群公告列表
     * @param group 群号
     * @param number 数量
     * @return  群公告列表
     */
    GroupNoteList getGroupNoteList(String group, int number);

    /**
     * 取置顶群公告
     * @param group 群号
     * @return  置顶群公告
     */
    GroupTopNote getGroupTopNote(String group);

    /**
     * 获取图片信息
     * @param flag  图片文件名或标识
     * @return  图片信息
     */
    ImageInfo getImageInfo(String flag);

    /**
     * 获取登录的QQ的信息
     * @return 登录QQ的信息
     */
    LoginQQInfo getLoginQQInfo();

    /**
     * 获取群共享文件列表
     * @return 共享文件列表
     */
    ShareList getShareList(String group);

    /**
     * 取陌生人信息 使用缓存
     * @param QQ 陌生人的QQ号
     * @return
     */
    default StrangerInfo getStrangerInfo(String QQ){
        return getStrangerInfo(QQ, CACHE);
    }

    /**
     * 取陌生人信息
     * @param QQ 陌生人的QQ号
     * @param cache 是否使用缓存
     * @return
     */
    StrangerInfo getStrangerInfo(String QQ, boolean cache);

}
