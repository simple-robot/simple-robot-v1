package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * 「取运行状态」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:17
 * @since JDK1.8
 **/
public class Req_getRunStatus implements ReqBean {

    private final String fun =  "getRunStatus";

    @Override
    public String getFun() {
        return fun;
    }
}
