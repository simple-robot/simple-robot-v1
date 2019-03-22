package com.forte.qqrobot.HttpApi.bean.request.send;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:24
 * @since JDK1.8
 **/
public interface ReqSendBean extends ReqBean {

    /** 增加一个获取信息 */
    String getMsg();
    void setMsg(String msg);



}
