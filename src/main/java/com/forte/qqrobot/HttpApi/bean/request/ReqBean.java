package com.forte.qqrobot.HttpApi.bean.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * 用于请求参数的数据
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:27
 * @since JDK1.8
 **/
public interface ReqBean {

    /**
     * 请求的fun
     */
    String getFun();


    /**
     * 将自身转化为JSON字符串
     */
    default String toJson(){
        return JSON.toJSONString(this);
    }


}
