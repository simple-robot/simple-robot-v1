package com.forte.qqrobot.code;

import com.forte.qqrobot.beans.cqcode.CatCode;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface CodeConversionable<T extends CatCode> {

    /**
     * 将一个CatCode转化为指定的类型
     * @param catCode catCode
     * @return 转化后的子类
     */
    T toCode(CatCode catCode);

}
