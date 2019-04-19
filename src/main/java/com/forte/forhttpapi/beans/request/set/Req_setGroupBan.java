package com.forte.forhttpapi.beans.request.set;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:46
 * @since JDK1.8
 **/
public class Req_setGroupBan implements ReqBanBean {

    private final String fun = "setGroupBan";

    /** 群号 */
    private String group;
    /** QQ号，可空，空时为禁言全群 */
    private String qq;
    /** 禁言时间，单位：秒。0为解禁；不得超过 2592000 (一个月) */
    private Long time;

    @Override
    public String getFun() {
        return fun;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        //设置不可超过最大时间
        this.time = time > MAX_BAN_TIME ? MAX_BAN_TIME : (time < MIN_BAN_TIME ? MIN_BAN_TIME : time);
    }

}
