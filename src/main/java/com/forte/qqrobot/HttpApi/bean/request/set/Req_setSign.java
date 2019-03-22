package com.forte.qqrobot.HttpApi.bean.request.set;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:10
 * @since JDK1.8
 **/
public class Req_setSign implements ReqBean {
    private final String fun = "setSign";


    @Override
    public String getFun() {
        return fun;
    }
}
