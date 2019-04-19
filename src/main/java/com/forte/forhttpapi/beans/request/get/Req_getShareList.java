package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getShareList;

/**
 * 「取群文件列表」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:18
 * @since JDK1.8
 **/
public class Req_getShareList implements ReqGetBean<Resp_getShareList> {

    private final String fun = "getShareList";

    /** 群号 */
    private String group;

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

    @Override
    public Class<Resp_getShareList> getResponseType() {
        return Resp_getShareList.class;
    }
}
