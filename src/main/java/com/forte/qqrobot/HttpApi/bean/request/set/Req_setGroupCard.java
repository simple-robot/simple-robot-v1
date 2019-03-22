package com.forte.qqrobot.HttpApi.bean.request.set;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「置群成员名片」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:52
 * @since JDK1.8
 **/
public class Req_setGroupCard implements ReqBean {

    private final String fun = "setGroupCard";

    /** 群号 */
    private String group;
    /** qq号 */
    private String qq;
    /** 新名片 */
    private String card;




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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
