package com.forte.qqrobot.sender;

import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 实现了RootSenderList，并通过三个真实的senderList做代理。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ProxyRootSender implements RootSenderList {

    private SenderSendList sender;
    private SenderSetList setter;
    private SenderGetList getter;

    /**
     * 构造，提供三大送信器实例
     * @param sender sender送信器
     * @param setter setter送信器
     * @param getter getter送信器
     */
    public ProxyRootSender(SenderSendList sender, SenderSetList setter, SenderGetList getter) {
        this.sender = sender;
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public AnonInfo getAnonInfo(String flag) {
        return getter.getAnonInfo(flag);
    }

    @Override
    public AuthInfo getAuthInfo() {
        return getter.getAuthInfo();
    }

    @Override
    public BanList getBanList(String group) {
        return getter.getBanList(group);
    }

    @Override
    public FileInfo getFileInfo(String flag) {
        return getter.getFileInfo(flag);
    }

    @Override
    public FriendList getFriendList() {
        return getter.getFriendList();
    }

    @Override
    public GroupHomeworkList getGroupHomeworkList(String group, int number) {
        return getter.getGroupHomeworkList(group, number);
    }

    @Override
    public GroupInfo getGroupInfo(String group, boolean cache) {
        return getter.getGroupInfo(group, cache);
    }

    @Override
    public GroupLinkList getGroupLinkList(String group, int number) {
        return getter.getGroupLinkList(group, number);
    }

    @Override
    public GroupList getGroupList() {
        return getter.getGroupList();
    }

    @Override
    public GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache) {
        return getter.getGroupMemberInfo(group, QQ, cache);
    }

    @Override
    public GroupMemberList getGroupMemberList(String group) {
        return getter.getGroupMemberList(group);
    }

    @Override
    public GroupNoteList getGroupNoteList(String group, int number) {
        return getter.getGroupNoteList(group, number);
    }

    @Override
    public GroupTopNote getGroupTopNote(String group) {
        return getter.getGroupTopNote(group);
    }

    @Override
    public ImageInfo getImageInfo(String flag) {
        return getter.getImageInfo(flag);
    }

    @Override
    public LoginQQInfo getLoginQQInfo() {
        return getter.getLoginQQInfo();
    }

    @Override
    public ShareList getShareList(String group) {
        return getter.getShareList(group);
    }

    @Override
    public StrangerInfo getStrangerInfo(String QQ, boolean cache) {
        return getter.getStrangerInfo(QQ, cache);
    }

    //**************************************
    //*               SENDER
    //**************************************


    @Override
    public String sendDiscussMsg(String group, String msg) {
        return sender.sendDiscussMsg(group, msg);
    }

    @Override
    public String sendGroupMsg(String group, String msg) {
        return sender.sendGroupMsg(group, msg);
    }

    @Override
    public String sendPrivateMsg(String QQ, String msg) {
        return sender.sendPrivateMsg(QQ, msg);
    }

    @Override
    public boolean sendFlower(String group, String QQ) {
        return sender.sendFlower(group, QQ);
    }

    @Override
    public boolean sendLike(String QQ, int times) {
        return sender.sendLike(QQ, times);
    }

    @Override
    public boolean sendGroupNotice(String group, String title, String text, boolean top, boolean toNewMember, boolean confirm) {
        return sender.sendGroupNotice(group, title, text, top, toNewMember, confirm);
    }

    //**************************************
    //*             SETTER
    //**************************************


    @Override
    public boolean setFriendAddRequest(String flag, String friendName, boolean agree) {
        return setter.setFriendAddRequest(flag, friendName, agree);
    }

    @Override
    public boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why) {
        return setter.setGroupAddRequest(flag, requestType, agree, why);
    }

    @Override
    public boolean setGroupAdmin(String group, String QQ, boolean set) {
        return setter.setGroupAdmin(group, QQ, set);
    }

    @Override
    public boolean setGroupAnonymous(String group, boolean agree) {
        return setter.setGroupAnonymous(group, agree);
    }

    @Override
    public boolean setGroupAnonymousBan(String group, String flag, long time) {
        return setter.setGroupAnonymousBan(group, flag, time);
    }

    @Override
    public boolean setGroupBan(String group, String QQ, long time) {
        return setter.setGroupBan(group, QQ, time);
    }

    @Override
    public boolean setGroupCard(String group, String QQ, String card) {
        return setter.setGroupCard(group, QQ, card);
    }

    @Override
    public boolean setGroupFileDelete(String group, String flag) {
        return setter.setGroupFileDelete(group, flag);
    }

    @Override
    public boolean setDiscussLeave(String group) {
        return setter.setDiscussLeave(group);
    }


    @Override
    public boolean setGroupLeave(String group, boolean dissolve) {
        return setter.setGroupLeave(group, dissolve);
    }

    @Override
    public boolean setGroupMemberKick(String group, String QQ, boolean dontBack) {
        return setter.setGroupMemberKick(group, QQ, dontBack);
    }

    @Override
    public boolean setGroupSign(String group) {
        return setter.setGroupSign(group);
    }

    @Override
    public boolean setGroupExclusiveTitle(String group, String QQ, String title, long time) {
        return setter.setGroupExclusiveTitle(group, QQ, title, time);
    }

    @Override
    public boolean setGroupWholeBan(String group, boolean in) {
        return setter.setGroupWholeBan(group, in);
    }

    @Override
    public boolean setMsgRecall(String flag) {
        return setter.setMsgRecall(flag);
    }

    @Override
    public boolean setSign() {
        return setter.setSign();
    }
}
