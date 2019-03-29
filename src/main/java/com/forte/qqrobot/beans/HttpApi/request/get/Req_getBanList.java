package com.forte.qqrobot.beans.HttpApi.request.get;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;
import com.forte.qqrobot.beans.HttpApi.response.Resp_getBanList;

/**
 * 「取群中被禁言成员列表」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:44
 * @since JDK1.8
 **/
public class Req_getBanList implements ReqGetBean<Resp_getBanList> {

    private final String fun = "getBanList";
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
    public Class<Resp_getBanList> getResponseType() {
        return Resp_getBanList.class;
    }
}
