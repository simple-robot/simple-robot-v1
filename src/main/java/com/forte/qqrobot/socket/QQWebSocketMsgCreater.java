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
public class QQWebSocketMsgCreater {

    /**
     * 创建一个map
     * @param params 参数集，数组每一个元素的0索引为key，1索引为value
     * @return map
     */
    private static Map<String, Object> createParamMap(String[]... params){
        return Arrays.stream(params).collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

    /**
     * 创建一个map
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
    public static String getResponseJson_sendDisGroupMsg(String discussid, String msg){
        Map<String, Object> paramMap = createParamMap(
                new String[]{"discussid", discussid},
                new String[]{"msg", msg}
        );

        return mapToBeanJson(new SendDisGroupMsg(), paramMap);
    }

    /**
     * 群消息
     * @return 群消息
     */
    public static String getResponseJson_sendGroupMsg(String groupid, String msg){
        Map<String, Object> paramMap = createParamMap(
                new String[]{"groupid", groupid},
                new String[]{"msg", msg}
        );

        return mapToBeanJson(new SendGroupMsg(), paramMap);
    }

    /**
     * 私信消息
     * @param QQID  qq号
     * @param msg   消息
     * @return 私信
     */
    public static String getResponseJson_sendMsgPrivate(String QQID, String msg){
        Map<String, Object> paramMap = createParamMap(
                new String[]{"QQID", QQID},
                new String[]{"msg", msg}
        );
        return mapToBeanJson(new SendMsgPrivate(), paramMap);
    }

    /**
     * 赞
     * @param QQID qq号
     * @return 赞
     */
    public static String getResponseJson_sendPraise(String QQID){
        Map<String, Object> paramMap = createParamMap(new String[]{"QQID", QQID});
        return mapToBeanJson(new SendPraise(), paramMap);
    }

    /**
     * 设置全群禁言
     * @param QQID      qq号
     * @param groupid   群号
     * @param enableban 是否开启禁言
     * @return  全群禁言
     */
    public static String getResponseJson_setAllGroupBanned(String QQID, String groupid, String enableban){
        Map<String, Object> paramMap = createParamMap(
                new String[]{"QQID", QQID},
                new String[]{"groupid", groupid},
                new String[]{"enableban", enableban}
        );

        return mapToBeanJson(new SetAllGroupBanned(), paramMap);
    }
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_
//    public static String getResponseJson_


}
