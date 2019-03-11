package com.forte.qqrobot.beans.infoget;

/**
 * 25302,获取登录昵称
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:27
 * @since JDK1.8
 **/
public class InfoLoginNick implements InfoGet {
    /*
    25302,获取登录昵称
    请求json串只有一个act
    返回json串字段：
    error, act, return,LoginNick
     */
    /** 编号 */
    private static final Integer act = 25302;

    public Integer getAct() {
        return act;
    }
}
