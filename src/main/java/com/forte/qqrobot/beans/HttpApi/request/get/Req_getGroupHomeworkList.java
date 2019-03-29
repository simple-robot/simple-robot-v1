package com.forte.qqrobot.beans.HttpApi.request.get;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;
import com.forte.qqrobot.beans.HttpApi.response.Resp_getGroupHomeworkList;

/**
 * 「取群作业列表」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:47
 * @since JDK1.8
 **/
public class Req_getGroupHomeworkList implements ReqGetBean<Resp_getGroupHomeworkList> {

    private final String fun = "getGroupHomeworkList";

    /** 群号 */
    private String group;
    /** 取出数量 */
    private Integer number;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public Class<Resp_getGroupHomeworkList> getResponseType() {
        return Resp_getGroupHomeworkList.class;
    }
}
