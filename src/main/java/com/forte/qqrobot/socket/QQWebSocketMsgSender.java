package com.forte.qqrobot.socket;

import com.forte.qqrobot.beans.msgsend.*;

import java.util.Map;

/**
 * QQWebsocket信息发送器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 18:32
 * @since JDK1.8
 **/
public class QQWebSocketMsgSender {

    /** 客户端连接client对象 */
    private final QQWebSocketClient client;

    /** 响应信息字符串生成器 MsgCreator */
    private final QQWebSocketMsgCreator creator;

    /** 构造 */
    public QQWebSocketMsgSender(QQWebSocketClient client, QQWebSocketMsgCreator creator){
        this.client = client;
        this.creator = creator;

    }

    /* ———————————————— 信息发送方法 ———————————————— */

    /**
     * 讨论组消息
     * @param discussid 讨论组
     * @param msg       消息
     */
    public void sendResponseJson_sendDisGroupMsg(String discussid, String msg){
        client.send(creator.getResponseJson_sendDisGroupMsg(discussid, msg));
    }

    /**
     * 群消息
     * @param groupid   群号
     * @param msg       消息
     */
    public void sendResponseJson_sendGroupMsg(String groupid, String msg){
        client.send(creator.getResponseJson_sendGroupMsg(groupid, msg));
    }

    /**
     * 私信消息
     * @param QQID  qq号
     * @param msg   消息
     */
    public void sendResponseJson_sendMsgPrivate(String QQID, String msg){
        System.out.println(creator.getResponseJson_sendMsgPrivate(QQID, msg));
        System.out.println(client);
        client.send(creator.getResponseJson_sendMsgPrivate(QQID, msg));
    }

    /**
     * 赞
     * @param QQID qq号
     */
    public void sendResponseJson_sendPraise(String QQID){
        client.send(creator.getResponseJson_sendPraise(QQID));
    }

    /**
     * 设置全群禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param enableban 是否开启禁言
     */
    public void sendResponseJson_setAllGroupBanned(String QQID, String groupid, String enableban){
        client.send(creator.getResponseJson_setAllGroupBanned(QQID, groupid, enableban));
    }

    /**
     * 设置匿名群员禁言
     * @param groupid   群号
     * @param duration  时间，单位 秒
     * @param anomymous 匿名群员名称，大概
     */
    public void sendResponseJson_setAnoGroupMemberBanned(String groupid, Long duration, String anomymous){
        client.send(creator.getResponseJson_setAnoGroupMemberBanned(groupid, duration, anomymous));
    }

    /**
     * 讨论组退出
     * @param discussid 讨论组id
     */
    public void sendResponseJson_setDisGroupExit(String discussid){
        client.send(creator.getResponseJson_setDisGroupExit(discussid));
    }

    /**
     * 好友添加请求
     * @param responseoperation
     * @param remark
     * @param responseflag
     */
    public void sendResponseJson_setFriendAddRequest(String responseoperation, String remark, String responseflag){
        client.send(creator.getResponseJson_setFriendAddRequest(responseoperation, remark, responseflag));
    }

    /**
     * 设置群管理员
     * @param QQID      qq号
     * @param groupid   群号
     * @param setadmin  是否设置为管理员
     */
    public void sendResponseJson_setGroupAdmin(String QQID, String groupid, Boolean setadmin){
        client.send(creator.getResponseJson_setGroupAdmin(QQID, groupid, setadmin));
    }

    /**
     * 群匿名设置
     * @param groupid           群号
     * @param enableanomymous   是否开启群匿名
     */
    public void sendResponseJson_setGroupAno(String groupid, Boolean enableanomymous){
        client.send(creator.getResponseJson_setGroupAno(groupid, enableanomymous));
    }

    /**
     * 置群退出(出于安全起见，该权限没有开启)
     * @param groupid   群号
     * @param isdismiss 是否退出
     */
    public void sendResponseJson_setGroupExit(String groupid, Boolean isdismiss){
        client.send(creator.getResponseJson_setGroupExit(groupid, isdismiss));
    }

    /**
     *  置群添加请求
     *  参数意义暂且不明
     * @param requesttype
     * @param responseoperation
     * @param reason
     * @param responseflag
     */
    public void sendResponseJson_setGroupJoinResquest(String requesttype, String responseoperation, String reason, String responseflag){
        client.send(creator.getResponseJson_setGroupJoinResquest(requesttype, responseoperation, reason, responseflag));
    }

    /**
     * 置群员禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param duration  禁言时间，单位为秒
     */
    public void sendResponseJson_setGroupMemberBanned(String QQID, String groupid, Long duration){
        client.send(creator.getResponseJson_setGroupMemberBanned(QQID, groupid, duration));
    }

    /**
     * 置群成员名片
     * @param QQID      qq号
     * @param groupid   群号
     * @param newcard   新名片
     */
    public void sendResponseJson_setGroupMemberCard(String QQID, String groupid, String newcard){
        client.send(creator.getResponseJson_setGroupMemberCard(QQID, groupid, newcard));
    }

    /**
     * 置群员移除
     * @param QQID              移除的qq号
     * @param groupid           群号
     * @param rejectaddrequest  是否拒绝添加请求
     */
    public void sendResponseJson_setGroupMemberRemove(String QQID, String groupid, Boolean rejectaddrequest){
        client.send(creator.getResponseJson_setGroupMemberRemove(QQID, groupid, rejectaddrequest));
    }

    /**
     *  置群成员专属头衔
     * @param QQID              qq号
     * @param groupid           群号
     * @param duration          设置时间，单位大概是秒
     * @param newspecialtitle   专属头衔
     */
    public void sendResponseJson_setGroupMemberSpecialTitle(String QQID, String groupid, Long duration, String newspecialtitle){
        client.send(creator.getResponseJson_setGroupMemberSpecialTitle(QQID, groupid, duration, newspecialtitle));
    }




}
