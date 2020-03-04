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
