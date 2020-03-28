package com.forte.qqrobot.utils;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;

import java.util.function.Function;

/**
 * 主要是为了开发者提供的，json字符串转化为json对象的工具类
 * json的转化由阿里的fastJson提供
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class JSONUtils {

    private static final String ORIGINAL_NAME = "originalData";

    /**
     * 将json字符串转化为JSON对象
     */
    public static JSONObject toJsonObject(String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        jsonObject.put(ORIGINAL_NAME, jsonStr);
        return jsonObject;
    }

    /**
     * 根据JSONObject对象来将json对象转化为对应的消息获取对象
     */
    public static <T extends MsgGet> T toMsgGet(JSONObject jsonObject, Function<JSONObject, Class<T>> toType){
        Class<T> apply = toType.apply(jsonObject);
        return apply == null ? null : jsonObject.toJavaObject(apply);
    }

    /**
     * 根据json字符串将json对象转化为对应的消息获取对象
     */
    public static <T extends MsgGet> T toMsgGet(String jsonStr, Function<JSONObject, Class<T>> toType){
        JSONObject jsonObject = toJsonObject(jsonStr);
        Class<T> apply = toType.apply(jsonObject);
        return apply == null ? null : jsonObject.toJavaObject(apply);
    }

}
