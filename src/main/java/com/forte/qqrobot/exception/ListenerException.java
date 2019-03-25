package com.forte.qqrobot.exception;

import com.forte.qqrobot.listener.SocketListener;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/12 16:55
 * @since JDK1.8
 **/
public class ListenerException extends RobotException {

    public ListenerException(SocketListener listener, String info) {
        super("监听器["+ listener +"]出现异常：" + info);
    }

    public ListenerException(SocketListener listener) {
        super("监听器["+ listener +"]出现异常");
    }
}
