package com.forte.qqrobot.beans.HttpApi.request.set;

/**
 * 「置全群禁言」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:06
 * @since JDK1.8
 **/
public class Req_setGroupWholeBan implements ReqBanBean {
    private final String fun = "setGroupWholeBan";


    /** 群号 */
    private String group;
    /** 操作类型，true/开启禁言，false/关闭禁言 */
    private Boolean open;

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
