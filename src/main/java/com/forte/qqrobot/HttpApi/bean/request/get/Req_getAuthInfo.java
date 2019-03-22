package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「取权限信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:31
 * @since JDK1.8
 **/
public class Req_getAuthInfo implements ReqBean {
    /*
    {
    "fun":"getAuthInfo"
}
     */

    private final String fun = "getAuthInfo";

    @Override
    public String getFun() {
        return fun;
    }
}
