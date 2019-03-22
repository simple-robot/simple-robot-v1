package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;
import com.forte.qqrobot.HttpApi.bean.response.Resp_getGroupMemberInfo;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:55
 * @since JDK1.8
 **/
public class Req_getGroupMemberInfo implements ReqGetBean<Resp_getGroupMemberInfo> {

    private final String fun = "getGroupMemberInfo";
    /** 群号 */
    private String qq;

    /** qq号 */
    private int cache;

    /** 使用缓存，0/不使用，1/使用 */
    private String group;


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
