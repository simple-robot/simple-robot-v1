package com.forte.qqrobot.beans.msgget;

/**
 * 未知的消息，用于解决可能出现的空指针异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 10:30
 * @since JDK1.8
 **/
public class UnknownMsg implements MsgGet {

    private static final Integer act = -999;

    private static final String MSG = null;

    @Override
    public Integer getAct() {
        return act;
    }

    @Override
    public String getMsg() {
        return MSG;
    }

    @Override
    public void setMsg(String msg) {
    }


}

