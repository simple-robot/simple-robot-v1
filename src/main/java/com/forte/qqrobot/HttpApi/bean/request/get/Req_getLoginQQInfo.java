package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「取登录QQ的信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:06
 * @since JDK1.8
 **/
public class Req_getLoginQQInfo implements ReqBean {

    private final String fun = "getLoginQQInfo";

    @Override
    public String getFun() {
        return fun;
    }
}
