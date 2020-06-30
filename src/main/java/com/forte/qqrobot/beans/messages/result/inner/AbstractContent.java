/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     AbstractContent.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.AbstractResultInner;

/**
 * Content对应抽象类
 * @see Content
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractContent extends AbstractResultInner implements Content {

    private String text;

    private String type;

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Content{" +
                "text='" + getText() + '\'' +
                ", type='" + getType() + '\'' +
                "} " + super.toString();
    }
}
