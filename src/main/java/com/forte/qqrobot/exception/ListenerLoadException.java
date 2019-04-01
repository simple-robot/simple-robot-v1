package com.forte.qqrobot.exception;

import com.forte.qqrobot.listener.SocketListener;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/1 14:44
 * @since JDK1.8
 **/
public class ListenerLoadException extends ListenerException {

    public ListenerLoadException(SocketListener listener, String info) {
        super(listener, info);
    }

    public ListenerLoadException(SocketListener listener) {
        super(listener);
    }
}
