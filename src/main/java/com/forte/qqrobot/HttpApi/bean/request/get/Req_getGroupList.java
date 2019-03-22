package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「取群列表」 有异常
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:55
 * @since JDK1.8
 **/
public class Req_getGroupList implements ReqBean {

    private final String fun = "getGroupList";

    @Override
    public String getFun() {
        return fun;
    }
}
