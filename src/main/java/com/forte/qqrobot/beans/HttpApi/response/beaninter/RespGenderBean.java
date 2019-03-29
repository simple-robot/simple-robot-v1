package com.forte.qqrobot.beans.HttpApi.response.beaninter;

/**
 * 为有性别信息的消息封装提供字符串格式化
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/28 14:48
 * @since JDK1.8
 **/
public interface RespGenderBean {
    /*
        性别ID，0/男性，1/女性，255/未知
     */

    int MAN_GENDER = 0;
    int WOMAN_GENDER = 1;
    int UNKNOWN_GENDER = 255;

    String MAN_STR = "男";
    String WOMAN_STR = "女";
    String UNKNOWN_STR = "未知";

    Integer getGender();

    /**
     * 性别格式化字符串
     */
    default String genderToString(){
        return isMan() ? MAN_STR : isWoman() ? WOMAN_STR : UNKNOWN_STR;
    }

    default boolean isMan(){
        return getGender() == MAN_GENDER;
    }

    default boolean isWoman(){
        return getGender() == WOMAN_GENDER;
    }

    default boolean isUnknown(){
        return getGender() == UNKNOWN_GENDER;
    }

}
