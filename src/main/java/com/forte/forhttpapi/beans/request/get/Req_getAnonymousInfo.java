package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getAnonymousInfo;

/**
 * 「取匿名成员信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:29
 * @since JDK1.8
 **/
public class Req_getAnonymousInfo implements ReqGetBean<Resp_getAnonymousInfo> {
    /*
    请求数据
    {
        "fun":"getAnonymousInfo",
        "source":"AAAAAyys="
    }
     */
    /**
     * 请求函数名称
     */
    private final String fun = "getAnonymousInfo";

    /**
     * 匿名成员的标识，即插件提交的参数fromAnonymous
     */
    private String source;

    @Override
    public String getFun() {
        return fun;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Class<Resp_getAnonymousInfo> getResponseType() {
        return Resp_getAnonymousInfo.class;
    }
}
