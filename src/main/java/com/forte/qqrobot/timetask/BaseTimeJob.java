/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     BaseTimeJob.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.timetask;

import org.quartz.Job;

/**
 * {@link TimeJob} 有时候去实现会出现问题，无法正确覆盖{@link Job}中的方法，故此提供一个新的抽象类以代替原本的TimeJob接口。
 * <br>
 * 由于有人出现过default方法会报错的问题，所以试试在接口列表里写个job能不能解决这个问题
 * @author ForteScarlet <ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseTimeJob implements TimeJob, Job {

}
