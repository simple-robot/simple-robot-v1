package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getGroupTopNote;

/**
 * 「取群置顶公告」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:03
 * @since JDK1.8
 **/
public class Req_getGroupTopNote implements ReqGetBean<Resp_getGroupTopNote> {

    private final String fun = "getGroupTopNote";
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
    public Class<Resp_getGroupTopNote> getResponseType() {
        return Resp_getGroupTopNote.class;
    }
}
