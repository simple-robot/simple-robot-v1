package com.forte.qqrobot.HttpApi.bean.request.set;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「置好友添加请求」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:36
 * @since JDK1.8
 **/
public class Req_setFriendAddRequest implements ReqBean {

    private final String fun = "setFriendAddRequest";

    /** 反馈标识，请求事件收到的responseFlag参数 */
    private String responseFlag;
    /** 反馈类型，1/通过 2/拒绝 */
    private Integer subType;
    /** 备注，仅 反馈类型 为 通过 时有效 */
    private String name;

    @Override
    public String getFun() {
        return fun;
    }

    public String getResponseFlag() {
        return responseFlag;
    }

    public void setResponseFlag(String responseFlag) {
        this.responseFlag = responseFlag;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
