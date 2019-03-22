package com.forte.qqrobot.HttpApi.bean.request.get;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.HttpApi.bean.request.ReqBean;
import com.forte.qqrobot.HttpApi.bean.response.RespBean;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:20
 * @since JDK1.8
 **/
public interface ReqGetBean<T extends RespBean<?>> extends ReqBean {

    Class<T> getResponseType();

    /**
     * 将返回值的json字符串转化为实例对象
     * @param responseJsonStr json字符串
     * @return 实例
     */
    default T parseResponse(String responseJsonStr){
        return JSON.parseObject(responseJsonStr, getResponseType());
    }

}
