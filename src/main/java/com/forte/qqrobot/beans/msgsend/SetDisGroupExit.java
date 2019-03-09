package com.forte.qqrobot.beans.msgsend;

/**
 * 140, 置讨论组退出
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 12:08
 * @since JDK1.8
 **/
public class SetDisGroupExit {
    /*
    140, 置讨论组退出
    discussid
     */
    /** 编号 */
    private static final Integer act = 140;
    /** 要退出的讨论组 */
    private String discussid;



    public Integer getAct() {
        return act;
    }

    public String getDiscussid() {
        return discussid;
    }

    public void setDiscussid(String discussid) {
        this.discussid = discussid;
    }
}
