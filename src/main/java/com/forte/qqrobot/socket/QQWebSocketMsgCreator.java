package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.beans.HttpApi.request.get.*;
import com.forte.qqrobot.beans.infoget.*;
import com.forte.qqrobot.beans.msgsend.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * QQWebsocket-信息生成器，用于生成返回给服务器的方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 18:37
 * @since JDK1.8
 **/
public class QQWebSocketMsgCreator {

    /**
     * 创建一个参数map
     * @param params 参数集，数组每一个元素的0索引为key，1索引为value
     * @return map
     */
    private static Map<String, Object> createParamMap(Object[]... params){
        return Arrays.stream(params).collect(Collectors.toMap(p -> p[0]+"", p -> p[1]));
    }

    /**
     * 创建一个初始参数map
     * @param init map初始大小
     * @return map
     */
    private static Map<String, Object> createParamMap(int init){
        return new HashMap<>(init);
    }

    /**
     * 将一个空对象和map参数集合传进来，返回封装完的json字符串
     * @return
     */
    private static String mapToBeanJson(Object bean, Map<String, Object> params){
        //封装对象
        try {
            //将参数封装进去，然后转化为JSON对象
            BeanUtils.populate(bean, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("参数封装失败！");
            e.printStackTrace();
        }

        return JSON.toJSONString(bean);
    }


    /* ———————————————— 参数转JSON方法集 ———————————————— */

    //**************************************
    //*             发送的消息
    //**************************************


    /**
     * 讨论组消息
     * @return 讨论组消息
     */
    public String getResponseJson_sendDisGroupMsg(String discussid, String msg){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"discussid", discussid},
                new Object[]{"msg", msg}
        );

        return mapToBeanJson(new SendDisGroupMsg(), paramMap);
    }

    /**
     * 群消息
     * @return 群消息
     */
    public String getResponseJson_sendGroupMsg(String groupid, String msg){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupid", groupid},
                new Object[]{"msg", msg}
        );

        return mapToBeanJson(new SendGroupMsg(), paramMap);
    }

    /**
     * 私信消息
     * @param QQID  qq号
     * @param msg   消息
     * @return 私信
     */
    public String getResponseJson_sendMsgPrivate(String QQID, String msg){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"msg", msg}
        );
        return mapToBeanJson(new SendMsgPrivate(), paramMap);
    }


    //**************************************
    //*             设置的消息 - socket
    //**************************************


    /**
     * 赞
     * @param QQID qq号
     * @return 赞
     */
    public String getResponseJson_sendPraise(String QQID){
        Map<String, Object> paramMap = createParamMap(new Object[]{"QQID", QQID});
        return mapToBeanJson(new SendPraise(), paramMap);
    }



    /**
     * 设置全群禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param enableban 是否开启禁言
     * @return  全群禁言
     */
    public String getResponseJson_setAllGroupBanned(String QQID, String groupid, String enableban){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"enableban", enableban}
        );

        return mapToBeanJson(new SetAllGroupBanned(), paramMap);
    }

    /**
     * 设置匿名群员禁言
     * @param groupid   群号
     * @param duration  时间，单位 秒
     * @param anomymous 匿名群员名称，大概
     * @return 设置匿名群员禁言
     */
    public String getResponseJson_setAnoGroupMemberBanned(String groupid, Long duration, String anomymous){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupid", groupid},
                new Object[]{"duration", duration},
                new Object[]{"anomymous", anomymous}
        );

        return mapToBeanJson(new SetAnoGroupMemberBanned(), paramMap);
    }

    /**
     * 讨论组退出
     * @param discussid 讨论组id
     * @return 讨论组退出
     */
    public String getResponseJson_setDisGroupExit(String discussid){
        Map<String, Object> paramMap = createParamMap(new Object[]{"discussid", discussid});
        return mapToBeanJson(new SetDisGroupExit(), paramMap);
    }

    /**
     * 好友添加请求
     * @param responseoperation
     * @param remark
     * @param responseflag
     * @return
     */
    public String getResponseJson_setFriendAddRequest(String responseoperation, String remark, String responseflag){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"responseoperation", responseoperation},
                new Object[]{"remark", remark},
                new Object[]{"responseflag", responseflag}
        );
        return mapToBeanJson(new SetFriendAddRequest(), paramMap);
    }

    /**
     * 设置群管理员
     * @param QQID      qq号
     * @param groupid   群号
     * @param setadmin  是否设置为管理员
     * @return 群管理员
     */
    public String getResponseJson_setGroupAdmin(String QQID, String groupid, Boolean setadmin){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"setadmin", setadmin}
        );
        return mapToBeanJson(new SetGroupAdmin(), paramMap);
    }

    /**
     * 群匿名设置
     * @param groupid           群号
     * @param enableanomymous   是否开启群匿名
     * @return  群匿名设置
     */
    public String getResponseJson_setGroupAno(String groupid, Boolean enableanomymous){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupid", groupid},
                new Object[]{"enableanomymous", enableanomymous}
        );
        return mapToBeanJson(new SetGroupAno(), paramMap);
    }

    /**
     * 置群退出(出于安全起见，该权限没有开启)
     * @param groupid   群号
     * @param isdismiss 是否退出
     * @return 置群退出
     */
    public String getResponseJson_setGroupExit(String groupid, Boolean isdismiss){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupid", groupid},
                new Object[]{"isdismiss", isdismiss}
        );
        return mapToBeanJson(new SetGroupExit(), paramMap);
    }

    /**
     *  置群添加请求
     * @param requesttype
     * @param responseoperation
     * @param reason
     * @param responseflag
     * @return  置群添加请求
     */
    public String getResponseJson_setGroupJoinResquest(String requesttype, String responseoperation, String reason, String responseflag){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"requesttype", requesttype},
                new Object[]{"responseoperation", responseoperation},
                new Object[]{"reason", reason},
                new Object[]{"responseflag", responseflag}
        );
        return mapToBeanJson(new SetGroupJoinResquest(), paramMap);
    }

    /**
     * 置群员禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param duration  禁言时间，单位为秒
     * @return 置群员禁言
     */
    public String getResponseJson_setGroupMemberBanned(String QQID, String groupid, Long duration){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"duration", duration}
        );
        return mapToBeanJson(new SetGroupMemberBanned(), paramMap);
    }

    /**
     * 置群成员名片
     * @param QQID      qq号
     * @param groupid   群号
     * @param newcard   新名片
     * @return  群成员名片
     */
    public String getResponseJson_setGroupMemberCard(String QQID, String groupid, String newcard){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"newcard", newcard}
        );
        return mapToBeanJson(new SetGroupMemberCard(), paramMap);
    }

    /**
     * 置群员移除
     * @param QQID              移除的qq号
     * @param groupid           群号
     * @param rejectaddrequest  是否拒绝添加请求
     * @return
     */
    public String getResponseJson_setGroupMemberRemove(String QQID, String groupid, Boolean rejectaddrequest){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"rejectaddrequest", rejectaddrequest}
        );
        return mapToBeanJson(new SetGroupMemberRemove(), paramMap);
    }

    /**
     *  置群成员专属头衔
     * @param QQID              qq号
     * @param groupid           群号
     * @param duration          设置时间，单位大概是秒
     * @param newspecialtitle   专属头衔
     * @return  群成员专属头衔
     */
    public String getResponseJson_setGroupMemberSpecialTitle(String QQID, String groupid, Long duration, String newspecialtitle){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"groupid", groupid},
                new Object[]{"duration", duration},
                new Object[]{"newspecialtitle", newspecialtitle}
        );
        return mapToBeanJson(new SetGroupMemberSpecialTitle(), paramMap);
    }


    //**************************************
    //*             信息请求的消息 - socket
    //**************************************


    /**
     * 请求获取群列表信息
     * @param QQID 作为标记
     * @return 请求群列表信息
     */
    public String getResponseJson_InfoForLoginInGroups(String QQID){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID}
        );
        return mapToBeanJson(new SetGroupMemberSpecialTitle(), paramMap);
    }

    /**
     * 请求获取群中某用户信息
     * @param groupid   群号
     * @param QQID      群员qq号
     * @param nocache   1或0之类的
     * @return 请求获取群中某用户信息
     */
    public String getResponseJson_InfoGroupMember(String groupid, String QQID, String nocache){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupid", groupid},
                new Object[]{"QQID", QQID},
                new Object[]{"nocache", nocache}
        );
        return mapToBeanJson(new InfoGroupMemberForJson(), paramMap);
    }

    /**
     * 请求获取当前账号的昵称
     * @return 请求获取当前账号的昵称
     */
    public String getResponseJson_InfoLoginNick(){
        return JSON.toJSONString(new InfoLoginNick());
    }

    /**
     * 请求获取当前账号的qq
     * @return 请求获取当前账号的qq
     */
    public String getResponseJson_InfoLoginQQ(){
        return JSON.toJSONString(new InfoLoginQQ());
    }

    /**
     * 请求获取陌生人信息
     * @param QQID      qq号
     * @param nocache   1 或 0 之类的
     * @return  请求获取陌生人信息
     */
    public String getResponseJson_InfoStranger(String QQID, String nocache){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"QQID", QQID},
                new Object[]{"nocache", nocache}
        );
        return mapToBeanJson(new InfoStrangerForJson(), paramMap);
    }


    //**************************************
    //*             信息请求的消息 - HTTP API
    //**************************************

    /**
     * 取匿名成员信息
     * @param source 匿名成员的标识，即插件提交的参数fromAnonymous
     * @return 匿名成员信息
     */
    public String getResponseJson_Req_getAnonymousInfo(String source){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"source", source}
        );
        return mapToBeanJson(new Req_getAnonymousInfo(), paramMap);
    }

    /**
     * 取权限信息
     * @return 权限信息
     */
    public String getResponseJson_Req_getAuthInfo(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getAuthInfo(), paramMap);
    }

    /**
     * 取群中被禁言成员列表
     * @param group 群号
     * @return 群中被禁言成员列表
     */
    public String getResponseJson_Req_getBanList(String group){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group}
        );

        return mapToBeanJson(new Req_getBanList(), paramMap);
    }

    /**
     * 取文件信息
     * @param source 文件标识，即插件所提交的参数file
     * @return 件信息
     */
    public String getResponseJson_Req_getFileInfo(String source){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"source", source}
        );
        return mapToBeanJson(new Req_getFileInfo(), paramMap);
    }

    /**
     * 取好友列表 (尚且有异常
     * @return 好友列表
     */
    @Deprecated
    public String getResponseJson_Req_getFriendList(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getFriendList(), paramMap);
    }

    /**
     * 取群作业列表
     * @param group 群号
     * @param number 取出数量
     * @return
     */
    public String getResponseJson_Req_getGroupHomeworkList(String group, Integer number){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group},
                new Object[]{"number", number}
        );
        return mapToBeanJson(new Req_getGroupHomeworkList(), paramMap);
    }

    /**
     * 取群详细信息
     * @param group 群号
     * @return 群详细信息
     */
    public String getResponseJson_Req_getGroupInfo(String group){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group}
        );
        return mapToBeanJson(new Req_getGroupInfo(), paramMap);
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 取出数量
     * @return 群链接列表
     */
    public String getResponseJson_Req_getGroupLinkList(String group, Integer number){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group},
                new Object[]{"number", number}
        );
        return mapToBeanJson(new Req_getGroupLinkList(), paramMap);
    }

    /**
     * 取群列表 (尚有异常
     * @return 群列表
     */
    @Deprecated
    public String getResponseJson_Req_getGroupList(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getGroupList(), paramMap);
    }

    /**
     * 取群成员信息
     * @param qq qq号
     * @param group 群号
     * @param cache 使用缓存，0/不使用，1/使用
     * @return 群成员信息
     */
    public String getResponseJson_Req_getGroupMemberInfo(String qq, String group, Integer cache){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"qq", qq},
                new Object[]{"group", group},
                new Object[]{"cache", cache}
        );
        return mapToBeanJson(new Req_getGroupMemberInfo(), paramMap);
    }

    /**
     * 取群成员列表
     * @param group 群号
     * @return 群成员列表
     */
    public String getResponseJson_Req_getGroupMemberList(String group){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group}
        );
        return mapToBeanJson(new Req_getGroupMemberList(), paramMap);
    }

    /**
     * 取群公告列表
     * @return 群公告列表
     */
    public String getResponseJson_Req_getGroupNoteList(String group, Integer number){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group},
                new Object[]{"number", number}
        );
        return mapToBeanJson(new Req_getGroupNoteList(), paramMap);
    }

    /**
     * 取群置顶公告
     * @return 群置顶公告
     */
    public String getResponseJson_Req_getGroupTopNote(String group){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group}
        );
        return mapToBeanJson(new Req_getGroupTopNote(), paramMap);
    }

    /**
     * 取图片信息 ※ 只能获取酷Q接收到的图片
     * @param source 图片文件名
     * @param needFile 需要回传文件内容
     * @return 图片信息
     */
    public String getResponseJson_Req_getImageInfo(String source, Boolean needFile){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"source", source},
                new Object[]{"needFile", needFile}
        );
        return mapToBeanJson(new Req_getImageInfo(), paramMap);
    }

    /**
     * 取登录QQ的信息
     * @return 登录QQ的信息
     */
    public String getResponseJson_Req_getLoginQQInfo(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getLoginQQInfo(), paramMap);
    }

    /**
     * 批量取群头像
     * @param groupList 群列表，每个群用-分开，可空，空时表示取所有群的头像链接
     * @return
     */
    public String getResponseJson_Req_getMoreGroupHeadimg(String groupList){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"groupList", groupList}
        );
        return mapToBeanJson(new Req_getMoreGroupHeadimg(), paramMap);
    }

    /**
     * 批量取QQ信息
     * @param qqList Q列表，每个QQ用-分开
     * @return 批量取QQ信息
     */
    public String getResponseJson_Req_getMoreQQInfo(String qqList){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"qqList", qqList}
        );
        return mapToBeanJson(new Req_getMoreQQInfo(), paramMap);
    }

    /**
     * 接收语音文件 需要权限30
     * @param source 文件名，必须是消息中的语音文件(file)
     * @param format 目标编码，默认MP3, 目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac
     * @param needFile 是否回传文件数据，true/回传，false/不回传
     * @return
     */
    public String getResponseJson_Req_getRecord(String source, Req_getRecord.RecordType format, Boolean needFile){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"source", source},
                new Object[]{"format", format},
                new Object[]{"needFile", needFile}
        );

        return mapToBeanJson(new Req_getRecord(), paramMap);
    }

    /**
     * 取运行状态
     * @return 运行状态
     */
    public String getResponseJson_Req_getRunStatus(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getRunStatus(), paramMap);
    }

    /**
     * 取群文件列表
     * @param group 群号
     * @return 群文件列表
     */
    public String getResponseJson_Req_getShareList(String group){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"group", group}
        );
        return mapToBeanJson(new Req_getShareList(), paramMap);
    }

    /**
     * 取陌生人信息
     * 需要权限131
     * 可能需要权限20
     * @param cache 使用缓存，true/使用，false/不使用
     * @return
     */
    public String getResponseJson_Req_getStrangerInfo(Boolean cache){
        Map<String, Object> paramMap = createParamMap(
                new Object[]{"cache", cache}
        );
        return mapToBeanJson(new Req_getStrangerInfo(), paramMap);
    }

    /**
     * 获取版本？
     * @return 版本？
     */
    public String getResponseJson_Req_getVersion(){
        Map<String, Object> paramMap = createParamMap(0);
        return mapToBeanJson(new Req_getVersion(), paramMap);
    }


}
