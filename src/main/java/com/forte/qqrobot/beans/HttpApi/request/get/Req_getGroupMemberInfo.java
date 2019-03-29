package com.forte.qqrobot.beans.HttpApi.request.get;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;
import com.forte.qqrobot.beans.HttpApi.response.Resp_getGroupMemberInfo;

/**
 * 取群成员信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:55
 * @since JDK1.8
 **/
public class Req_getGroupMemberInfo implements ReqGetBean<Resp_getGroupMemberInfo> {

    private final String fun = "getGroupMemberInfo";

    /** qq号 */
    private String qq;
    /** 群号 */
    private String group;

    /** 使用缓存，0/不使用，1/使用 */
    private Integer cache;



    @Override
    public String getFun() {
        return fun;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public Class<Resp_getGroupMemberInfo> getResponseType() {
        return Resp_getGroupMemberInfo.class;
    }
}
