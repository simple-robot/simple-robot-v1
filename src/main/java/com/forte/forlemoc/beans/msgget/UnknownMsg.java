package com.forte.forlemoc.beans.msgget;


/**
 * 未知的消息，用于解决可能出现的空指针异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/11 10:30
 * @since JDK1.8
 **/
public class UnknownMsg implements MsgGet {

    private static final Integer act = -999;

    private static final String MSG = null;

    public Integer getAct() {
        return act;
    }

    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    @Override
    public String getMsg() {
        return MSG;
    }

    /**
     * 获取消息的字体
     */
    @Override
    public String getFont() {
        return null;
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return 0;
    }

    public void setMsg(String msg) {
    }


}

