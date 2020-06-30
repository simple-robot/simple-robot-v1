/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     EmptyInputStream.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.utils;

import java.io.InputStream;

/**
 * 一个空的输入流
 * copy自4.4版本的httpcore
 * @since 4.4
 */
public final class EmptyInputStream extends InputStream {

    public static final EmptyInputStream INSTANCE = new EmptyInputStream();

    private EmptyInputStream() {
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public void close() {
    }

    @Override
    public void mark(final int readLimit) {
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() {
        return -1;
    }

    @Override
    public int read(final byte[] buf) {
        return -1;
    }

    @Override
    public int read(final byte[] buf, final int off, final int len) {
        return -1;
    }

    @Override
    public void reset() {
    }

    @Override
    public long skip(final long n) {
        return 0L;
    }
}

