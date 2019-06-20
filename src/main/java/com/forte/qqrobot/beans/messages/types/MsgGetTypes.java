package com.forte.qqrobot.beans.messages.types;


import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.beans.messages.msgget.*;
import com.forte.qqrobot.utils.FieldUtils;

/**
 * 枚举类型，定义全部的消息接收类型
 * @author ForteScarlet
 **/
public enum MsgGetTypes {


    /** 私信信息 */
    privateMsg(PrivateMsg.class),

    /** 讨论组信息 */
    discussMsg(DiscussMsg.class),

    /** 群消息 */
    groupMsg(GroupMsg.class),

    /** 事件-好友添加 */
    friendAdd(FriendAdd.class),

    /** 事件-管理员变动 */
    groupAdminChange(GroupAdminChange.class),

    /** 事件-群成员增加 */
    groupMemberIncrease(GroupMemberIncrease.class),

    /** 事件-群成员减少 */
    groupMemberReduce(GroupMemberReduce.class),

    /** 请求-添加好友 */
    friendAddRequest(FriendAddRequest.class),

    /** 请求-群添加 */
    groupAddRequest(GroupAddRequest.class),

    ;


    private Class<? extends MsgGet> beanClass;

    /**
     * 构造
     * @param beanClass bean的class对象
     */
    MsgGetTypes(Class<? extends MsgGet> beanClass){
        this.beanClass = beanClass;
    }

    /**
     * 获取此类型对应的class对象
     * @return 此类型对应的class对象
     */
    public Class<? extends MsgGet> getBeanClass(){
        return beanClass;
    }

    /**
     * 转化为对应的对象，使用Object类型接收
     * @param json
     * @return
     */
    public Object getBeanForJson(String json){
        return JSON.parseObject(json, beanClass);
    }


    /**
     * 通过class对象获取枚举对象
     * @param clazz class对象
     */
    public static MsgGetTypes getByType(Class<? extends MsgGet> clazz){
        for (MsgGetTypes type : values()) {
            if(FieldUtils.isChild(clazz, type.beanClass)){
                return type;
            }
        }

        return null;
    }

    /**
     * 通过MsgGet对象获取枚举对象
     * @param msgGet 对象
     */
    public static MsgGetTypes getByType(MsgGet msgGet){
       return getByType(msgGet.getClass());
    }


}
