package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;
import com.forte.qqrobot.HttpApi.bean.response.Resp_getGroupNoteList;

/**
 * 「取群公告列表」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:59
 * @since JDK1.8
 **/
public class Req_getGroupNoteList implements ReqGetBean<Resp_getGroupNoteList> {

    private final String fun = "getGroupNoteList";

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
    public Class<Resp_getGroupNoteList> getResponseType() {
        return Resp_getGroupNoteList.class;
    }
}
