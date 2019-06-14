package com.forte.qqrobot.beans.messages.get;

import com.forte.qqrobot.beans.messages.RootBean;
import com.forte.qqrobot.beans.messages.result.InfoResult;

/**
 * 消息获取类型，用于获取参数的对象
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface InfoGet<RESULT extends InfoResult> extends RootBean {

    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    Class<? extends RESULT> resultType();

    /**
     * 请求的时候都应该有一个参数标识
     */
    String getId();

}
