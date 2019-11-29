package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.ResultInner;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * 群成员信息
 */
public interface GroupMember extends ResultInner {
    /** 群号 */
    String getGroup();
    /** QQ号 */
    String getQQ();
    /** QQ名 */
    String getName();
    /** 获取群昵称 */
    String getNickName();

    /**
     * 获取昵称，如果没有设置昵称那么获取QQ名
     */
    default String getNickOrName(){
        String nick = getNickName();
        if(nick==null||nick.length() == 0){
            return getName();
        }else{
            return nick;
        }
    }

    /** 获取性别 */
    SexType getSex();
    /** 所在城市 */
    String getCity();

    /** 加群时间 */
    Long getJoinTime();
    /** 最后发言时间 */
    Long getLastTime();
    /** 权限类型 */
    PowerType getPower();

    /** 获取专属头衔 */
    String getExTitle();
    /** 等级对应名称 */
    String getLevelName();
    /** 是否为不良用户 */
    Boolean isBlack();
    /** 是否允许修改群名片 */
    Boolean isAllowChangeNick();

    /** 头衔到期时间 */
    Long getExTitleTime();

    /** 头像 */
    String getHeadUrl();


}
