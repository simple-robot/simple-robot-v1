package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * 「置群成员头衔」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:03
 * @since JDK1.8
 **/
public class Req_setGroupSpecialTitle implements ReqBean {

    private final String fun = "setGroupSpecialTitle";

    /** 群号 */
    private String group;
    /** qq号 */
    private String qq;
    /** 头衔，可空。如果要删除，这里填null */
    private String tip;
    /** 专属头衔有效期，单位为秒。如果永久有效，这里填写-1 */
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
