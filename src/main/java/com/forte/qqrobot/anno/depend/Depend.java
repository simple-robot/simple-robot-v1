package com.forte.qqrobot.anno.depend;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * 使用此注解对字段进行注入
 * 可以使用在字段、方法参数上<br>
 * - 字段上时，会对字段进行注入<br>
 *
 * - 方法参数上时，在调用此方法的时候会根据参数进行注入<br>
 * 方法注入中：
 * 仅有{@link #value()} 和 {@link #type()} 参数生效,
 * 优先考虑{@link #value()}，如果value没有填入则使用类型注入。
 * 假如不使用此注解也会进行注入，但是假若没有开启编译指令的话可能会无法正确注入
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({FIELD, PARAMETER})
public @interface Depend {

    /** 使用名称对依赖进行注入，如果为空字符串则使用类型进行注入 */
    String value() default "";

    /**
     * 如果要使用字段类型进行注入，但是此类型可能存在多个（不同实现类），<br>
     * 那么使用此参数来指定类型<br>
     * 虽然类型为数组，但是仅只能使用一个类型，如果有多个则只取第一个.<br>
     * Get the first element only
     */
    Class[] type() default {};

    /**
     * 是否当字段值不为null的时候再进行注入，一般用于避免与其他依赖注入的参数出现冲突，默认为true
     * 只有此值为true的时候{@link #byGetter()}才会生效
     * 否则将不进行判断，直接注入
     */
    boolean nonNull() default true;

    /** 是否使用getter方法对字段的可否注入进行判断，默认为false，即直接判断字段的值 */
    boolean byGetter() default false;

    /**
     * 如果{@link #byGetter()} 为true, 则可以使用此值对getter方法的方法名进行指定 <br>
     * 如果不指定则默认为通用的getter方法名，即get+字段名 <br>
     * 参数必定为空
     */
    String getterName() default "";

    /** 是否使用setter方法对字段进行赋值注入，默认为false，即直接对字段值进行操作 */
    boolean bySetter() default false;

    /**
     * 如果{@link #bySetter()} 为true, 则可以使用此值对setter方法的方法名进行指定 <br>
     * 如果不指定则默认为通用的setter方法名，即set + 字段名 <br>
     * 参数为一个字段自身类型的参数
     */
    String setterName() default "";

}
