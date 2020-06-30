/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     TypeFactoryException.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.factory;

import com.forte.qqrobot.exception.RobotRuntimeException;

/**
 *
 * 枚举工厂使用的异常类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TypeFactoryException extends RobotRuntimeException {

    public TypeFactoryException() {
    }

    public TypeFactoryException(String message) {
        super(message);
    }

    public TypeFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeFactoryException(Throwable cause) {
        super(cause);
    }

    public TypeFactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
