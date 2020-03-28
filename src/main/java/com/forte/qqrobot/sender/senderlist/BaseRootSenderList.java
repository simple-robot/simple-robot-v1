package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.exception.RobotApiException;
import com.forte.qqrobot.sender.BaseAPITemplate;
import com.forte.qqrobot.sender.HttpClientAble;
import com.forte.qqrobot.sender.HttpClientHelper;

/**
 * 送信器整合，全部抛出异常
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseRootSenderList implements RootSenderList {
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

    /**
     * 发送讨论组消息
     *
     * @param group 群号
     * @param msg   消息内容
     */
    @Override
    public String sendDiscussMsg(String group, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送群消息
     *
     * @param group 群号
     * @param msg   消息内容
     */
    @Override
    public String sendGroupMsg(String group, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送私聊信息
     *
     * @param QQ  QQ号
     * @param msg 消息内容
     */
    @Override
    public String sendPrivateMsg(String QQ, String msg) {
        throw RobotApiException.byFrom();
    }

    /**
     * 送花
     *
     * @param group 群号
     * @param QQ    QQ号
     */
    @Override
    public boolean sendFlower(String group, String QQ) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发送名片赞
     *
     * @param QQ    QQ号
     * @param times 次数
     */
    @Override
    public boolean sendLike(String QQ, int times) {
        throw RobotApiException.byFrom();
    }

    /**
     * 发布群公告
     * 目前，top、toNewMember、confirm参数是无效的
     *
     * @param group       群号
     * @param title       标题
     * @param text        正文
     * @param top         是否置顶，默认false
     * @param toNewMember 是否发给新成员 默认false
     * @param confirm     是否需要确认 默认false
     * @return 是否发布成功
     */
    @Override
    public boolean sendGroupNotice(String group, String title, String text, boolean top, boolean toNewMember, boolean confirm) {
        throw RobotApiException.byFrom();
    }


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
     * 内部提供一个默认实现的接口
     *
     * @param group 群号
     */
    @Override
    public boolean setGroupSign(String group) {
        return setGroupSign(group, "签到", "签到");
    }

    /**
     * 群签到
     * 内部提供一个默认实现的接口
     *
     * @param group 群号
     */
    public boolean setGroupSign(String group, String poi, String text) {
        try {
            // 提供一个默认的登录接口
            // 获取权限信息
            final AuthInfo authInfo = getAuthInfo();
            // 获取送信器
            final HttpClientAble http = HttpClientHelper.getDefaultHttp();
            BaseAPITemplate.groupSign(http, authInfo, group, poi, text);
            return true;
        } catch (Exception e) {
            throw RobotApiException.byFrom();
        }
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
