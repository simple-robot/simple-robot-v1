package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.HttpApi.bean.request.get.*;
import com.forte.qqrobot.HttpApi.bean.response.*;
import com.forte.qqrobot.LinkConfiguration;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.inforeturn.*;
import com.forte.qqrobot.utils.HttpClientUtil;

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

    /** 私有构造 */
    private QQWebSocketMsgSender(QQWebSocketClient client, QQWebSocketMsgCreator creator){
        this.client = client;
        this.creator = creator;

    }

    /**
     * 工厂方法
     * @return QQWebSocketMsgSender实例对象
     */
    public static QQWebSocketMsgSender of(QQWebSocketClient client){
        return new QQWebSocketMsgSender(client, ResourceDispatchCenter.getQQWebSocketMsgCreator());
    }

    /* ———————————————— 信息发送方法 ———————————————— */

    /**
     * 讨论组消息
     * @param discussid 讨论组
     * @param msg       消息
     */
    public void sendDisGroupMsg(String discussid, String msg){
        send(creator.getResponseJson_sendDisGroupMsg(discussid, msg));
    }

    /**
     * 群消息
     * @param groupid   群号
     * @param msg       消息
     */
    public void sendGroupMsg(String groupid, String msg){
        send(creator.getResponseJson_sendGroupMsg(groupid, msg));
    }

    /**
     * 私信消息
     * @param QQID  qq号
     * @param msg   消息
     */
    public void sendMsgPrivate(String QQID, String msg){
        send(creator.getResponseJson_sendMsgPrivate(QQID, msg));
    }

    /**
     * 赞
     * @param QQID qq号
     */
    public void sendPraise(String QQID){
        send(creator.getResponseJson_sendPraise(QQID));
    }

    /**
     * 设置全群禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param enableban 是否开启禁言
     */
    public void setAllGroupBanned(String QQID, String groupid, String enableban){
        send(creator.getResponseJson_setAllGroupBanned(QQID, groupid, enableban));
    }

    /**
     * 设置匿名群员禁言
     * @param groupid   群号
     * @param duration  时间，单位 秒
     * @param anomymous 匿名群员名称，大概
     */
    public void setAnoGroupMemberBanned(String groupid, Long duration, String anomymous){
        send(creator.getResponseJson_setAnoGroupMemberBanned(groupid, duration, anomymous));
    }

    /**
     * 讨论组退出
     * @param discussid 讨论组id
     */
    public void setDisGroupExit(String discussid){
        send(creator.getResponseJson_setDisGroupExit(discussid));
    }

    /**
     * 好友添加请求
     * @param responseoperation
     * @param remark
     * @param responseflag
     */
    public void setFriendAddRequest(String responseoperation, String remark, String responseflag){
        send(creator.getResponseJson_setFriendAddRequest(responseoperation, remark, responseflag));
    }

    /**
     * 设置群管理员
     * @param QQID      qq号
     * @param groupid   群号
     * @param setadmin  是否设置为管理员
     */
    public void setGroupAdmin(String QQID, String groupid, Boolean setadmin){
        send(creator.getResponseJson_setGroupAdmin(QQID, groupid, setadmin));
    }

    /**
     * 群匿名设置
     * @param groupid           群号
     * @param enableanomymous   是否开启群匿名
     */
    public void setGroupAno(String groupid, Boolean enableanomymous){
        send(creator.getResponseJson_setGroupAno(groupid, enableanomymous));
    }

    /**
     * 置群退出(出于安全起见，该权限没有开启)
     * @param groupid   群号
     * @param isdismiss 是否退出
     */
    public void setGroupExit(String groupid, Boolean isdismiss){
        send(creator.getResponseJson_setGroupExit(groupid, isdismiss));
    }

    /**
     *  置群添加请求
     *  参数意义暂且不明
     * @param requesttype
     * @param responseoperation
     * @param reason
     * @param responseflag
     */
    public void setGroupJoinResquest(String requesttype, String responseoperation, String reason, String responseflag){
        send(creator.getResponseJson_setGroupJoinResquest(requesttype, responseoperation, reason, responseflag));
    }

    /**
     * 置群员禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param duration  禁言时间，单位为秒
     */
    public void setGroupMemberBanned(String QQID, String groupid, Long duration){
        send(creator.getResponseJson_setGroupMemberBanned(QQID, groupid, duration));
    }

    /**
     * 置群成员名片
     * @param QQID      qq号
     * @param groupid   群号
     * @param newcard   新名片
     */
    public void setGroupMemberCard(String QQID, String groupid, String newcard){
        send(creator.getResponseJson_setGroupMemberCard(QQID, groupid, newcard));
    }

    /**
     * 置群员移除
     * @param QQID              移除的qq号
     * @param groupid           群号
     * @param rejectaddrequest  是否拒绝添加请求
     */
    public void setGroupMemberRemove(String QQID, String groupid, Boolean rejectaddrequest){
        send(creator.getResponseJson_setGroupMemberRemove(QQID, groupid, rejectaddrequest));
    }

    /**
     *  置群成员专属头衔
     * @param QQID              qq号
     * @param groupid           群号
     * @param duration          设置时间，单位大概是秒
     * @param newspecialtitle   专属头衔
     */
    public void setGroupMemberSpecialTitle(String QQID, String groupid, Long duration, String newspecialtitle){
        send(creator.getResponseJson_setGroupMemberSpecialTitle(QQID, groupid, duration, newspecialtitle));
    }


    //**************************************
    //*         获取信息-LEMOC socket
    //**************************************


    /**
     * 获取全部群信息
     * @param QQID 标记用QQ号
     * @return 群信息的列表
     */
    public ReturnLoginInGroups getLoginInGroups(String QQID){
        int act = 25305;
        send(creator.getResponseJson_InfoForLoginInGroups(QQID));
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        //标记一次更新
        manager.send(act);

        return manager.get(act, ReturnLoginInGroups.class);
    }

    /**
     * 获取某个群员的信息
     * @param groupid   群id
     * @param QQID      qq号
     * @param nocache   1 或 0之类的
     * @return 某个群员的信息
     */
    public ReturnGroupMember getGroupMember(String groupid, String QQID, String nocache){
        int act = 25303;
        send(creator.getResponseJson_InfoGroupMember(groupid, QQID, nocache));
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        //标记一次更新
        manager.send(act);

        return manager.get(act, ReturnGroupMember.class);

    }

    /**
     * 获取登录账号的昵称
     * @return 昵称
     */
    public ReturnLoginNick getLoginNick(){
        int act = 25302;
        send(creator.getResponseJson_InfoLoginNick());
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        manager.send(act);

        return manager.get(act, ReturnLoginNick.class);
    }

    /**
     * 获取当前登录账号的qq号
     * @return 当前登录账号的qq号
     */
    public ReturnLoginQQ getLoginQQ(){
        int act = 25301;
        send(creator.getResponseJson_InfoLoginQQ());
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        manager.send(act);

        return manager.get(act, ReturnLoginQQ.class);
    }

    /**
     * 获取陌生人信息
     * @return
     */
    public ReturnStranger getStrangerInfo(String QQID, String nocache){
        int act = 25304;
        send(creator.getResponseJson_InfoStranger(QQID, nocache));
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        manager.send(act);

        return manager.get(act, ReturnStranger.class);
    }


    //**************************************
    //*         获取信息-HTTP API
    //**************************************

    /**
     * 取匿名成员信息
     * @param source 匿名成员的标识，即插件提交的参数fromAnonymous
     * @return 匿名成员信息
     */
    public Resp_getAnonymousInfo getResp_getAnonymousInfo(String source){
        String json = creator.getResponseJson_Req_getAnonymousInfo(source);
        return get(json, Resp_getAnonymousInfo.class);
    }

    /**
     * 取权限信息
     * @return 权限信息
     */
    public Resp_getAuthInfo getResp_getAuthInfo(){
        String json = creator.getResponseJson_Req_getAuthInfo();
        return get(json, Resp_getAuthInfo.class);
    }

    /**
     * 取群中被禁言成员列表
     * @param group 群号
     * @return 群中被禁言成员列表
     */
    public Resp_getBanList getResp_getBanList(String group){
        String json = creator.getResponseJson_Req_getBanList(group);
        return get(json, Resp_getBanList.class);
    }

    /**
     * 取文件信息
     * @param source 文件标识，即插件所提交的参数file
     * @return 件信息
     */
    public Resp_getFileInfo getResp_getFileInfo(String source){
        String json = creator.getResponseJson_Req_getFileInfo(source);
        return get(json, Resp_getFileInfo.class);
    }

    /**
     * 取好友列表 (尚且有异常
     * @return 好友列表
     */
    @Deprecated
    public Resp_getFriendList getResp_getFriendList(){
        String json = creator.getResponseJson_Req_getFriendList();
        return get(json, Resp_getFriendList.class);
    }

    /**
     * 取群作业列表
     * @param group 群号
     * @param number 取出数量
     * @return
     */
    public Resp_getGroupHomeworkList getResp_getGroupHomeworkList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupHomeworkList(group, number);
        return get(json, Resp_getGroupHomeworkList.class);
    }

    /**
     * 取群详细信息
     * @param group 群号
     * @return 群详细信息
     */
    public Resp_getGroupInfo getResp_getGroupInfo(String group){
        String json = creator.getResponseJson_Req_getGroupInfo(group);
        return get(json, Resp_getGroupInfo.class);
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 取出数量
     * @return 群链接列表
     */
    public Resp_getGroupLinkList getResp_getGroupLinkList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupLinkList(group, number);
        return get(json, Resp_getGroupLinkList.class);
    }

    /**
     * 取群列表 (尚有异常
     * @return 群列表
     */
    @Deprecated
    public Resp_getGroupList getResp_getGroupList(){
        String json = creator.getResponseJson_Req_getGroupList();
        return get(json, Resp_getGroupList.class);
    }

    /**
     * 取群成员信息
     * @param qq qq号
     * @param group 群号
     * @param cache 使用缓存，0/不使用，1/使用
     * @return 群成员信息
     */
    public Resp_getGroupMemberInfo getResp_getGroupMemberInfo(String qq, String group, Integer cache){
        String json = creator.getResponseJson_Req_getGroupMemberInfo(qq, group, cache);
        return get(json, Resp_getGroupMemberInfo.class);
    }

    /**
     * 取群成员列表
     * @param group 群号
     * @return 群成员列表
     */
    public Resp_getGroupMemberList getResp_getGroupMemberList(String group){
        String json = creator.getResponseJson_Req_getGroupMemberList(group);
        return get(json, Resp_getGroupMemberList.class);
    }

    /**
     * 取群公告列表
     * @return 群公告列表
     */
    public Resp_getGroupNoteList getResp_getGroupNoteList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupNoteList(group, number);
        return get(json, Resp_getGroupNoteList.class);
    }

    /**
     * 取群置顶公告
     * @return 群置顶公告
     */
    public Resp_getGroupTopNote getResp_getGroupTopNote(String group){
        String json = creator.getResponseJson_Req_getGroupTopNote(group);
        return get(json, Resp_getGroupTopNote.class);
    }

    /**
     * 取图片信息 ※ 只能获取酷Q接收到的图片
     * @param source 图片文件名
     * @param needFile 需要回传文件内容
     * @return 图片信息
     */
    public Resp_getGroupTopNote getResponseJson_Req_getImageInfo(String source, Boolean needFile){
        String json = creator.getResponseJson_Req_getImageInfo(source, needFile);
        return get(json, Resp_getGroupTopNote.class);
    }

    /**
     * 取登录QQ的信息
     * @return 登录QQ的信息
     */
    public Resp_getLoginQQInfo getResp_getLoginQQInfo(){
        String json = creator.getResponseJson_Req_getLoginQQInfo();
        return get(json, Resp_getLoginQQInfo.class);
    }

    /**
     * 批量取群头像
     * @param groupList 群列表，每个群用-分开，可空，空时表示取所有群的头像链接
     * @return
     */
    public Resp_getMoreGroupHeadimg getResp_getMoreGroupHeadimg(String groupList){
        String json = creator.getResponseJson_Req_getMoreGroupHeadimg(groupList);
        return get(json, Resp_getMoreGroupHeadimg.class);
    }

    /**
     * 批量取QQ信息
     * @param qqList Q列表，每个QQ用-分开
     * @return 批量取QQ信息
     */
    public Resp_getMoreQQInfo getResp_getMoreQQInfo(String qqList){
        String json = creator.getResponseJson_Req_getMoreQQInfo(qqList);
        return get(json, Resp_getMoreQQInfo.class);
    }

    /**
     * 接收语音文件 需要权限30
     * @param source 文件名，必须是消息中的语音文件(file)
     * @param format 目标编码，默认MP3, 目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac
     * @param needFile 是否回传文件数据，true/回传，false/不回传
     * @return
     */
    public Resp_getRecord getResp_getRecord(String source, Req_getRecord.RecordType format, Boolean needFile){
        String json = creator.getResponseJson_Req_getRecord(source, format, needFile);
        return get(json, Resp_getRecord.class);
    }

    /**
     * 取运行状态
     * @return 运行状态
     */
    public Resp_getRunStatus getResp_getRunStatus(){
        String json = creator.getResponseJson_Req_getRunStatus();
        return get(json, Resp_getRunStatus.class);
    }

    /**
     * 取群文件列表
     * @param group 群号
     * @return 群文件列表
     */
    public Resp_getShareList getResp_getShareList(String group){
        String json = creator.getResponseJson_Req_getShareList(group);
        return get(json, Resp_getShareList.class);
    }

    /**
     * 取陌生人信息
     * 需要权限131
     * 可能需要权限20
     * @param cache 使用缓存，true/使用，false/不使用
     * @return
     */
    public Resp_getStrangerInfo getResp_getStrangerInfo(Boolean cache){
        String json = creator.getResponseJson_Req_getStrangerInfo(cache);
        return get(json, Resp_getStrangerInfo.class);
    }

    /**
     * 获取版本？
     * @return 版本？
     */
    public Resp_getVersion getResp_getVersion(){
        String json = creator.getResponseJson_Req_getVersion();
        return get(json, Resp_getVersion.class);
    }


    //**************************************
    //*          消息发送等方法
    //**************************************



    /**
     * 发送消息 - 在一条新的线程中发送消息
     */
    public void send(String text){
        ResourceDispatchCenter.getThreadPool().execute(() -> client.send(text));
    }

    /**
     * 通过HTTP API插件获取所需消息
     * @param json json参数字符串
     * @param beanType bean类型
     */
    public <T extends RespBean> T get(String json, Class<T> beanType){
        LinkConfiguration configuration = ResourceDispatchCenter.getLinkConfiguration();
        //获取HTTP API请求地址参数
        String url = configuration.getHttpRequestUrl();
        //返回参数
        String response = HttpClientUtil.sendHttpPost(url, json);
        //转化为bean对象
        return JSON.parseObject(response, beanType);
    }

    /**
     * 获取socket信息获取管理器
     * @return socket信息获取管理器
     */
    private QQWebSocketInfoReturnManager getSocketInfoReturnManager(){
        return ResourceDispatchCenter.getQQWebSocketInfoReturnManager();
    }

}
