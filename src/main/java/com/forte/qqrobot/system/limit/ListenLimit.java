/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     ListenLimit.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.system.limit;

/**
 *
 * 用于记录limit的时长
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ListenLimit {
    /**
     * 失效时间
     */
    private volatile long invalidTime = 0;

    /**
     * 有效时间
     */
    private final long effectiveTime;


    public ListenLimit(long effectiveTime){
        this.effectiveTime = effectiveTime;
    }

    /**
     * 检测是否已过期
     * 检测当前是否已经超过失效时间，且如果超过，刷新时间
     * @return 是否过期
     */
    public boolean expired(){
        final long now = System.currentTimeMillis();
        if(invalidTime < now){
            invalidTime = now + effectiveTime;
            return true;
        }
        return false;
    }





}
