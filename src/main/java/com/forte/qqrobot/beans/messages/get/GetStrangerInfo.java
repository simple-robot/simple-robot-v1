package com.forte.qqrobot.beans.messages.get;

import com.forte.qqrobot.beans.messages.result.StrangerInfo;

/**
 * 获取陌生人信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetStrangerInfo extends InfoGet<StrangerInfo> {


    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @Override
    default Class<? extends StrangerInfo> resultType(){
        return StrangerInfo.class;
    }

    String getQQ();
}
