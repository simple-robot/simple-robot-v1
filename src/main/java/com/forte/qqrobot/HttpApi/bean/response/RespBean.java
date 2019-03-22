package com.forte.qqrobot.HttpApi.bean.response;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:35
 * @since JDK1.8
 **/
public interface RespBean<T> {

    /**
     * 获取返回值状态码
     */
    Integer getStatus();

    /**
     * 获取返回值
     */
    T getResult();

    /**
     * 错误信息
     */
    String getErrMsg();

    /**
     * 是否错误
     * @return
     */
    default Boolean isErr(){
        return (getStatus() <= -400 || getStatus() >= -600);
    }

}
