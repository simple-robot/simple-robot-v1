package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.response.Resp_getVersion;

/**
 * 获取版本？
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:22
 * @since JDK1.8
 **/
public class Req_getVersion implements ReqGetBean<Resp_getVersion> {

    private final String fun = "getVersion";

    @Override
    public String getFun() {
        return fun;
    }

    @Override
    public Class<Resp_getVersion> getResponseType() {
        return Resp_getVersion.class;
    }
}
