package com.forte.qqrobot.HttpApi.bean.request.set;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「置群添加请求」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:39
 * @since JDK1.8
 **/
public class Req_setGroupAddRequest implements ReqBean {

    private final String fun = "setGroupAddRequest";

    /** 反馈标识，请求事件收到的responseFlag参数 */
    private String responseFlag;
    /** 请求类型，1/群添加，2/群邀请 */
    private Integer subType;
    /** 反馈类型，1/通过，2/拒绝 */
    private Integer type;
    /** 操作理由，可空，仅 群添加 & 拒绝 情况下有效 */
    private String msg;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
