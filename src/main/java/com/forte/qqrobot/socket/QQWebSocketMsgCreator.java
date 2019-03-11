package com.forte.qqrobot.socket;

import com.alibaba.fastjson.JSON;
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

}
