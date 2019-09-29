package com.forte.qqrobot.beans.messages.get;

import com.alibaba.fastjson.annotation.JSONField;
import com.forte.qqrobot.beans.messages.RootBean;
import com.forte.qqrobot.beans.messages.result.InfoResult;

/**
 * 消息获取类型，用于获取参数的对象
 * v1.0.4之后（不包括）的版本，所有消息获取类型的消息
 * 所有的消息获取类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface InfoGet<RESULT extends InfoResult> extends RootBean {

    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @JSONField(serialize = false)
    Class<? extends RESULT> resultType();

    /**
     * 请求的时候都应该有一个参数标识
     */
    String getId();

}
