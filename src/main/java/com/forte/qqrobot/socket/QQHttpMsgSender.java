package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.LinkConfiguration;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.HttpApi.request.get.Req_getRecord;
import com.forte.qqrobot.beans.HttpApi.response.*;
import com.forte.qqrobot.utils.HttpClientUtil;

import java.util.Optional;

/**
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/30 10:32
 * @since JDK1.8
 **/
public class QQHttpMsgSender {

    /** 响应信息字符串生成器 MsgCreator */
    private final QQJSONMsgCreator creator;

    /** 私有构造 */
    QQHttpMsgSender(QQJSONMsgCreator creator){
        this.creator = creator;
    }

    /**
     * 工厂方法
     * @return QQWebSocketMsgSender实例对象
     */
    public static QQHttpMsgSender build(){
        return new QQHttpMsgSender(ResourceDispatchCenter.getQQWebSocketMsgCreator());
    }
    
    //**************** 信息获取方法 ****************//

    //**************************************
    //*         获取信息-HTTP API
    //**************************************

    /**
     * 取匿名成员信息
     * @param source 匿名成员的标识，即插件提交的参数fromAnonymous
     * @return 匿名成员信息
     */
    public Optional<Resp_getAnonymousInfo> getAnonymousInfo(String source){
        String json = creator.getResponseJson_Req_getAnonymousInfo(source);
        return get(json, Resp_getAnonymousInfo.class);
    }

    /**
     * 取权限信息
     * @return 权限信息
     */
    public Optional<Resp_getAuthInfo> getAuthInfo(){
        String json = creator.getResponseJson_Req_getAuthInfo();
        return get(json, Resp_getAuthInfo.class);
    }

    /**
     * 取群中被禁言成员列表
     * @param group 群号
     * @return 群中被禁言成员列表
     */
    public Optional<Resp_getBanList> getBanList(String group){
        String json = creator.getResponseJson_Req_getBanList(group);
        return get(json, Resp_getBanList.class);
    }

    /**
     * 取文件信息
     * @param source 文件标识，即插件所提交的参数file
     * @return 件信息
     */
    public Optional<Resp_getFileInfo> getFileInfo(String source){
        String json = creator.getResponseJson_Req_getFileInfo(source);
        return get(json, Resp_getFileInfo.class);
    }

    /**
     * 取好友列表 (尚且有异常
     * @return 好友列表
     */
    @Deprecated
    public Optional<Resp_getFriendList> getFriendList(){
        String json = creator.getResponseJson_Req_getFriendList();
        return get(json, Resp_getFriendList.class);
    }

    /**
     * 取群作业列表
     * @param group 群号
     * @param number 取出数量
     * @return
     */
    public Optional<Resp_getGroupHomeworkList> getGroupHomeworkList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupHomeworkList(group, number);
        return get(json, Resp_getGroupHomeworkList.class);
    }

    /**
     * 取群详细信息
     * @param group 群号
     * @return 群详细信息
     */
    public Optional<Resp_getGroupInfo> getGroupInfo(String group){
        String json = creator.getResponseJson_Req_getGroupInfo(group);
        return get(json, Resp_getGroupInfo.class);
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 取出数量
     * @return 群链接列表
     */
    public Optional<Resp_getGroupLinkList> getGroupLinkList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupLinkList(group, number);
        return get(json, Resp_getGroupLinkList.class);
    }

    /**
     * 取群列表 (尚有异常
     * @return 群列表
     */
    @Deprecated
    public Optional<Resp_getGroupList> getGroupList(){
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
    public Optional<Resp_getGroupMemberInfo> getGroupMemberInfo(String qq, String group, Integer cache){
        String json = creator.getResponseJson_Req_getGroupMemberInfo(qq, group, cache);
        return get(json, Resp_getGroupMemberInfo.class);
    }

    /**
     * 取群成员列表
     * @param group 群号
     * @return 群成员列表
     */
    public Optional<Resp_getGroupMemberList> getGroupMemberList(String group){
        String json = creator.getResponseJson_Req_getGroupMemberList(group);
        return get(json, Resp_getGroupMemberList.class);
    }

    /**
     * 取群公告列表
     * @return 群公告列表
     */
    public Optional<Resp_getGroupNoteList> getGroupNoteList(String group, Integer number){
        String json = creator.getResponseJson_Req_getGroupNoteList(group, number);
        return get(json, Resp_getGroupNoteList.class);
    }

    /**
     * 取群置顶公告
     * @return 群置顶公告
     */
    public Optional<Resp_getGroupTopNote> getGroupTopNote(String group){
        String json = creator.getResponseJson_Req_getGroupTopNote(group);
        return get(json, Resp_getGroupTopNote.class);
    }

    /**
     * 取图片信息 ※ 只能获取酷Q接收到的图片
     * @param source 图片文件名
     * @param needFile 需要回传文件内容
     * @return 图片信息
     */
    public Optional<Resp_getGroupTopNote> getResponseJson_Req_getImageInfo(String source, Boolean needFile){
        String json = creator.getResponseJson_Req_getImageInfo(source, needFile);
        return get(json, Resp_getGroupTopNote.class);
    }

    /**
     * 取登录QQ的信息
     * @return 登录QQ的信息
     */
    public Optional<Resp_getLoginQQInfo> getLoginQQInfo(){
        String json = creator.getResponseJson_Req_getLoginQQInfo();
        return get(json, Resp_getLoginQQInfo.class);
    }

    /**
     * 批量取群头像
     * @param groupList 群列表，每个群用-分开，可空，空时表示取所有群的头像链接
     * @return
     */
    public Optional<Resp_getMoreGroupHeadimg> getMoreGroupHeadimg(String groupList){
        String json = creator.getResponseJson_Req_getMoreGroupHeadimg(groupList);
        return get(json, Resp_getMoreGroupHeadimg.class);
    }

    /**
     * 批量取QQ信息
     * @param qqList Q列表，每个QQ用-分开
     * @return 批量取QQ信息
     */
    public Optional<Resp_getMoreQQInfo> getMoreQQInfo(String qqList){
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
    public Optional<Resp_getRecord> getRecord(String source, Req_getRecord.RecordType format, Boolean needFile){
        String json = creator.getResponseJson_Req_getRecord(source, format, needFile);
        return get(json, Resp_getRecord.class);
    }

    /**
     * 取运行状态
     * @return 运行状态
     */
    public Optional<Resp_getRunStatus> getRunStatus(){
        String json = creator.getResponseJson_Req_getRunStatus();
        return get(json, Resp_getRunStatus.class);
    }

    /**
     * 取群文件列表
     * @param group 群号
     * @return 群文件列表
     */
    @Deprecated
    public Optional<Resp_getShareList> getShareList(String group){
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
    public Optional<Resp_getStrangerInfo> getStrangerInfo(Boolean cache){
        String json = creator.getResponseJson_Req_getStrangerInfo(cache);
        return get(json, Resp_getStrangerInfo.class);
    }

    /**
     * 获取版本？
     * @return 版本？
     */
    public Optional<Resp_getVersion> getVersion(){
        String json = creator.getResponseJson_Req_getVersion();
        return get(json, Resp_getVersion.class);
    }

    /**
     * 通过HTTP API插件获取所需消息
     * @param json json参数字符串
     * @param beanType bean类型
     */
    public <T extends RespBean> Optional<T> get(String json, Class<T> beanType){
        LinkConfiguration configuration = ResourceDispatchCenter.getLinkConfiguration();
        //获取HTTP API请求地址参数
        String url = configuration.getHttpRequestUrl();
        //返回参数
        String response = HttpClientUtil.sendHttpPost(url, json);
        //转化为bean对象，并做防止空指针的处理
        return Optional.ofNullable(response).map(res -> JSON.parseObject(res, beanType));
    }





}
