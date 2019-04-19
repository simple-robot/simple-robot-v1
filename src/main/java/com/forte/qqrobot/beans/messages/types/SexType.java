package com.forte.qqrobot.beans.messages.types;

/**
 * 性别相关枚举
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum SexType {


    MALE(-1, "男"),
    FEMALE(1, "女"),
    UNKNOWN(0, "未知")
    ;

    public final int SEX;
    public final String TO_STRING;

    SexType(int sex, String toString){
        this.SEX = sex;
        this.TO_STRING = toString;
    }

    public boolean isMale(){
        return SEX == MALE.SEX;
    }

    public boolean isFemale(){
        return SEX == FEMALE.SEX;
    }

    public boolean isUnknown(){
        return SEX == UNKNOWN.SEX;
    }

    @Override
    public String toString() {
        return TO_STRING;
    }}
