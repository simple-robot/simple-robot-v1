package com.forte.qqrobot.beans.HttpApi.request.get;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;
import com.forte.qqrobot.beans.HttpApi.response.Resp_getLoginQQInfo;

/**
 * 「取登录QQ的信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:06
 * @since JDK1.8
 **/
public class Req_getLoginQQInfo implements ReqGetBean<Resp_getLoginQQInfo> {

    private final String fun = "getLoginQQInfo";

    @Override
    public String getFun() {
        return fun;
    }

    @Override
    public Class<Resp_getLoginQQInfo> getResponseType() {
        return Resp_getLoginQQInfo.class;
    }
}
