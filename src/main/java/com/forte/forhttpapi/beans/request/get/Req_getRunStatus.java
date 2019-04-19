package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getRunStatus;

/**
 * 「取运行状态」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:17
 * @since JDK1.8
 **/
public class Req_getRunStatus implements ReqGetBean<Resp_getRunStatus> {

    private final String fun =  "getRunStatus";

    @Override
    public String getFun() {
        return fun;
    }

    @Override
    public Class<Resp_getRunStatus> getResponseType() {
        return Resp_getRunStatus.class;
    }
}
