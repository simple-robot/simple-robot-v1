/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     Listen.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.anno.data;

public class Listen {

	private final com.forte.qqrobot.beans.messages.types.MsgGetTypes[] value;


	/** 私有构造 */
	private Listen(com.forte.qqrobot.beans.messages.types.MsgGetTypes[] value){
		this.value = value;
	}

	/** 工厂方法 */
	public static Listen build(com.forte.qqrobot.beans.messages.types.MsgGetTypes[] value){
		return new Listen(value);
	}

	/** 工厂方法 */
	public static Listen build(com.forte.qqrobot.anno.Listen annotation) {
		return build(annotation.value());
	}


	/**
	 * 注解参数 {@link com.forte.qqrobot.anno.Listen#value()}
	 */
	public com.forte.qqrobot.beans.messages.types.MsgGetTypes[] value() {
		return value;
	}


}