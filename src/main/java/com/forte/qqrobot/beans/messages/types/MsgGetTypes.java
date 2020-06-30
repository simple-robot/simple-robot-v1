/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     MsgGetTypes.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.types;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.beans.messages.msgget.*;
import com.forte.qqrobot.utils.EnumValues;
import com.forte.qqrobot.utils.FieldUtils;

/**
 * 枚举类型，定义全部的消息接收类型,或者说监听类型
 *
 * @author ForteScarlet
 **/
public enum MsgGetTypes {


    /**
     * 私信信息
     */
    privateMsg(PrivateMsg.class),

    /**
     * 讨论组信息
     */
    discussMsg(DiscussMsg.class),

    /**
     * 群禁言事件
     */
    groupBan(GroupBan.class),

    /**
     * 群消息
     */
    groupMsg(GroupMsg.class),

    /**
     * 事件-好友添加
     */
    friendAdd(FriendAdd.class),

    /**
     * 事件-管理员变动
     */
    groupAdminChange(GroupAdminChange.class),

    /**
     * 事件-群成员增加
     */
    groupMemberIncrease(GroupMemberIncrease.class),

    /**
     * 事件-群成员减少
     */
    groupMemberReduce(GroupMemberReduce.class),

    /**
     * 请求-添加好友
     */
    friendAddRequest(FriendAddRequest.class),

    /**
     * 请求-群添加
     */
    groupAddRequest(GroupAddRequest.class),

    /**
     * 事件-群文件上传
     */
    groupFileUpload(GroupFileUpload.class),

    /**
     * 事件-群消息撤回
     */
    groupMsgDelete(GroupMsgDelete.class),

    /**
     * 私聊消息撤回
     */
    privateMsgDelete(PrivateMsgDelete.class),

    /**
     * 其他未知的类型，可尝试用于其他自定义监听
     * ※ 用处不大了，提供了枚举的自定义实例，此元素后期考虑删除。
     */
    @Deprecated
    unknownType(null) {
        /** 根据指定类型进行转化 */
        public <T> T getBeanForJson(String json, Class<T> type) {
            return JSON.parseObject(json, type);
        }

        /** 原本的转化类型将会直接转化为JSONObject对象 */
        @Override
        public JSONObject getBeanForJson(String json) {
            return JSON.parseObject(json);
        }
    },

//
//    MsgDeleted(null),
//    MsgDeleted(null),
//    MsgDeleted(null),
//    MsgDeleted(null),
//    MsgDeleted(null),
//    MsgDeleted(null),




    ;


    /** 对应的Bean类型 */
    private Class<? extends MsgGet> beanClass;

    /**
     * 构造
     *
     * @param beanClass bean的class对象
     */
    MsgGetTypes(Class<? extends MsgGet> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取此类型对应的class对象
     *
     * @return 此类型对应的class对象
     */
    public Class<? extends MsgGet> getBeanClass() {
        return beanClass;
    }

    /**
     * 转化为对应的对象，使用Object类型接收。
     * 此方法不会自动配置原始数据字符串
     *
     * @param json json字符串
     * @return
     */
    public Object getBeanForJson(String json) {
        return JSON.parseObject(json, beanClass);
    }


    /**
     * 通过class对象获取枚举对象
     *
     * @param clazz class对象
     */
    public static MsgGetTypes getByType(Class<? extends MsgGet> clazz) {
        if (clazz == null) {
            return unknownType;
        }

        for (MsgGetTypes type : EnumValues.values(MsgGetTypes.class, MsgGetTypes[]::new)) {
            if (type.beanClass != null && FieldUtils.isChild(clazz, type.beanClass)) {
                return type;
            }
        }

        return unknownType;
    }

    /**
     * 通过MsgGet对象获取枚举对象
     *
     * @param msgGet 对象
     */
    public static MsgGetTypes getByType(MsgGet msgGet) {
        return getByType(msgGet.getClass());
    }


}
