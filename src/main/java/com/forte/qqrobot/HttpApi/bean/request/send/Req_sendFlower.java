package com.forte.qqrobot.HttpApi.bean.request.send;

/**
 * 「送花」
 * 需要权限20
 * 需要账户中还有金豆
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:29
 * @since JDK1.8
 **/
public class Req_sendFlower implements ReqSendBean {

    private final String fun = "sendFlower";

    /** 群号 */
    private String group;
    /** QQ号 */
    private String qq;

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

    /**
     * 增加一个获取信息
     */
    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public void setMsg(String msg) {
    }
}
