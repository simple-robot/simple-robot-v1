package com.forte.qqrobot.beans.HttpApi.request.set;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;

/**
 * 「置群成员移除」
 *
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:57
 * @since JDK1.8
 **/
public class Req_setGroupKick implements ReqBean {

    private final String fun = "setGroupKick";

    /** 群号 */
    private String group;
    /** QQ号 */
    private String qq;
    /** 拒绝再加群，true/不再接收此人加群申请，false/接收此人加群申请 */
    private Boolean refuseJoin;


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

    public Boolean getRefuseJoin() {
        return refuseJoin;
    }

    public void setRefuseJoin(Boolean refuseJoin) {
        this.refuseJoin = refuseJoin;
    }
}
