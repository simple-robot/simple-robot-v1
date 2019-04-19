package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:41
 * @since JDK1.8
 **/
public class Req_setGroupAdmin implements ReqBean {

    private final String fun = "setGroupAdmin";


    /** 群号 */
    private String group;
    /** QQ号 */
    private String qq;
    /** 设置为管理员，True/设置，False/取消 */
    private Boolean become;


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

    public Boolean getBecome() {
        return become;
    }

    public void setBecome(Boolean become) {
        this.become = become;
    }
}
