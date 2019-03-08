package com.forte.qqrobot.beans.infoget;

/**
 * 25301,获取登录QQ号
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:26
 * @since JDK1.8
 **/
public class InfoLoginQQ {
    /*
        25301,获取登录QQ号
        请求json串只有一个act
        返回json串字段：
        error, act, return,LoginQQ
     */

    private final Integer act = 25301;



    public Integer getAct() {
        return act;
    }
}
