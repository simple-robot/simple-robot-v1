package com.forte.forlemoc.types;

import com.alibaba.fastjson.JSON;
import com.forte.forlemoc.beans.msgget.*;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 获取到的信息的枚举
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 17:40
 * @since JDK1.8
 **/
public enum MsgGetTypes {


    /** 私信信息 */
    msgPrivate(21, MsgPrivate.class),

    /** 讨论组信息 */
    msgDisGroup(4, MsgDisGroup.class),

    /** 群消息 */
    msgGroup(2, MsgGroup.class),

    /** 事件-好友添加 */
    eventFriendAdded(201, EventFriendAdded.class),

    /** 事件-管理员变动 */
    eventGroupAdmin(101, EventGroupAdmin.class),

    /** 事件-群成员增加 */
    eventGroupMemberJoin(103, EventGroupMemberJoin.class),

    /** 事件-群成员减少 */
    eventGroupMemberReduce(102, EventGroupMemberReduce.class),

    /** 请求-添加好友 */
    requestFriend(301, RequestFriend.class),

    /** 请求-群添加 */
    requestGroup(302, RequestGroup.class),

    /** 未知的消息 */
    unknownMsg(-999, UnknownMsg.class),
    ;


    private Integer act;
    private Class<? extends MsgGet> beanClass;
    //本类全部常量
    private static final AtomicReference<MsgGetTypes[]> allTypes = new AtomicReference<>();

    /**
     * 获取act-消息类型
     * @return  act-消息类型
     */
    public Integer getAct(){
        return act;
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
     * 构造
     * @param act       act码
     * @param beanClass bean的class对象
     */
    MsgGetTypes(Integer act, Class<? extends MsgGet> beanClass){
        this.act = act;
        this.beanClass = beanClass;
    }

    /**
     * 通过act获取枚举对象
     * @param act act
     */
    public static MsgGetTypes getByAct(Integer act){
        for (MsgGetTypes type : getAllTypes()) {
            if(type.act.equals(act)){
                return type;
            }
        }
        //没有找到，返回unknownMsg
        return unknownMsg;
//        switch (act){
//            case 21: return msgPrivate;
//            case 4: return msgDisGroup;
//            case 2: return msgGroup;
//            case 201: return eventFriendAdded;
//            case 101: return eventGroupAdmin;
//            case 103: return eventGroupMemberJoin;
//            case 102: return eventGroupMemberReduce;
//            case 301: return requestFriend;
//            case 302: return requestGroup;
//            default: return unknownMsg;
//        }
    }

    /**
     * 通过class对象获取枚举对象
     * @param clazz class对象
     */
    public static MsgGetTypes getByType(Class<? extends MsgGet> clazz){
        for (MsgGetTypes type : getAllTypes()) {
            if(type.beanClass.equals(clazz)){
                return type;
            }
        }
        //没有找到，返回unknownMsg
        return unknownMsg;
    }

    /**
     * 获取本类全部常量对象
     */
    private static MsgGetTypes[] getAllTypes(){
        return values();
//        return Optional.ofNullable(MsgGetTypes.allTypes.get()).orElseGet(() -> MsgGetTypes.allTypes.updateAndGet(pe -> MsgGetTypes.class.getEnumConstants()));
    }



}
