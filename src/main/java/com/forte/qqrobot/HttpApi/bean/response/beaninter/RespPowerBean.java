package com.forte.qqrobot.HttpApi.bean.response.beaninter;

/**
 * 群消息中，如果出现与权限相关的整数消息，实现此接口以获取字符串格式化等方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/28 15:00
 * @since JDK1.8
 **/
public interface RespPowerBean {
    /*
        管理权限，1/成员，2/管理，3/群主
     */

    int MEMBER = 1;
    int ADMIN = 2;
    int BOSS = 3;

    String MEMBER_STR = "群员";
    String ADMIN_STR = "管理";
    String BOSS_STR = "群主";
    String UNKNOWN_STR = "未知";

    /**
     * 获取权限信息
     * @return
     */
    Integer getPower();

    /**
     * 权限信息格式化
     */
    default String powerToString(){
        switch (getPower()){
            case MEMBER : return MEMBER_STR;
            case ADMIN : return ADMIN_STR;
            case BOSS : return BOSS_STR;
            default : return UNKNOWN_STR;
        }
    }

    /**
     * 是否为群员
     */
    default boolean isMember(){
        return getPower() == MEMBER;
    }

    /**
     * 是否为管理
     */
    default boolean isAdmin(){
        return getPower() == ADMIN;
    }

    /**
     * 是否为群主
     */
    default boolean isBoss(){
        return getPower() == BOSS;
    }

    /**
     * 权限是否比群主低
     */
    default boolean isLowerThanBoss(){
        return getPower() < BOSS;
    }

    /**
     * 权限是否比普通群员高
     */
    default boolean isHigherThanMember(){
        return getPower() > MEMBER;
    }




}
