package com.forte.qqrobot.listener.error;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.sender.MsgSender;

/**
 * 异常处理器接口
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface ExceptionHandle {

    /**
     * 进行异常处理
     * @param context 异常捕获对象封装
     * @return  异常处理后的响应结果。
     */
    Object handle(ExceptionHandleContext context);


}
