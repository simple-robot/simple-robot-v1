package com.forte.qqrobot.beans.msgget;

/**
 * 位置的消息，用于解决可能出现的空指针异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 10:30
 * @since JDK1.8
 **/
public class UnknownMsg implements MsgGet {

    private Integer act = -999;

    @Override
    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }
}

