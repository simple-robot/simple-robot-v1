/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     CodeConversionable.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.code;

import com.forte.qqrobot.beans.cqcode.CQCode;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface CodeConversionable<T extends CQCode> {

    /**
     * 将一个CatCode转化为指定的类型
     * @param CQCode catCode
     * @return 转化后的子类
     */
    T toCode(CQCode CQCode);

}
