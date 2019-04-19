package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getGroupList;

/**
 * 「取群列表」 有异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:55
 * @since JDK1.8
 **/
public class Req_getGroupList implements ReqGetBean<Resp_getGroupList> {

    private final String fun = "getGroupList";

    @Override
    public String getFun() {
        return fun;
    }

    @Override
    public Class<Resp_getGroupList> getResponseType() {
        return Resp_getGroupList.class;
    }
}
