package com.forte.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;

/**
 *
 * 给开发者我自己看的。
 * 标注一些可能需要大改的地方。
 *
 * Created by lcy on 2020/8/19.
 *
 * @author lcy
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({FIELD, TYPE, METHOD, PARAMETER, PACKAGE, CONSTRUCTOR})
public @interface SimbotTODO {
	String[] value() default {};
}
