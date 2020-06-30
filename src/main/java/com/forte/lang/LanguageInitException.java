/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     LanguageInitException.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.lang;

/**
 * 语言加载器初始化异常，不使用RobotException相关
 * 一般会抛出此异常的时候，都是语言还没有初始化完成的时候
 */
public class LanguageInitException extends RuntimeException {
    public LanguageInitException() {
    }

    public LanguageInitException(String message) {
        super(message);
    }

    public LanguageInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LanguageInitException(Throwable cause) {
        super(cause);
    }

    public LanguageInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
