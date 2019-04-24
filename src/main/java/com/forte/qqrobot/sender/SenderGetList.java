package com.forte.qqrobot.sender;

import com.forte.qqrobot.beans.messages.get.GetAnonInfo;
import com.forte.qqrobot.beans.messages.get.GetAuthInfo;
import com.forte.qqrobot.beans.messages.result.*;

/**
 * get相关方法列表，继承get总方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderGetList {

    //**************************************
    //*             get相关接口
    //**************************************

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
    GroupHomeworkList getGroupHomeworkList(String group);

    /**
     * 取群信息
     * @param group 群号
     * @return 群信息
     */
    GroupInfo getGroupInfo(String group);

    /**
     * 取群连接列表
     * @param groupList 群号
     * @return  群链接
     */
    GroupLinkList getGroupLinkList(String groupList);

    /**
     * 取群列表
     * @return 群列表
     */
    GroupList getGroupList();

    /**
     * 取群成员信息
     * @param group 群号
     * @param QQ    QQ号
     * @return 群成员信息
     */
    GroupMemberInfo getGroupMemberInfo(String group, String QQ);

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
    GroupNoteList getGroupNoteList(String group);

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
     * 取陌生人信息
     * @param QQ 陌生人的QQ号
     * @return
     */
    StrangerInfo getStrangerInfo(String QQ);

}
