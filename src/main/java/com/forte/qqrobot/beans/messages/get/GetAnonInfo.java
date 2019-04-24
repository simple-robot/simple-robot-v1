package com.forte.qqrobot.beans.messages.get;

import com.forte.qqrobot.beans.messages.result.AnonInfo;

/**
 * 获取匿名群员信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetAnonInfo extends InfoGet<AnonInfo> {

    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @Override
    default Class<AnonInfo> resultType(){
        return AnonInfo.class;
    }

    /**
     * 获取匿名消息标识
     */
    String getFlag();

}
