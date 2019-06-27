package com.forte.qqrobot.anno.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 普通的依赖，可以放置在方法上、类上
 * <ul>
 *     <li>
 * 类上：当在类上的时候，同时也会扫描类中的@Beans注解
 *      仅有此注解在类上的时候，{@link #constructor()}参数才会生效
 *      \@Constr注解的使用优先于constructor参数
 *     </li>
 *     <li>
 * 如果在方法上：当@Beans在方法上的时候，类上也必须有@Beans注解才会生效，否则将会被忽略。
 *      方法必须有返回值
 *     </li>
 *
 * </ul>
 *
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Retention(RetentionPolicy.RUNTIME)	//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.TYPE, ElementType.METHOD}) //类、方法
public @interface Beans {

    /** 依赖对象的名称，如果没有则以类名取代 */
    String value() default "";

    /** 是否为单例，默认为单例 */
    boolean single() default true;

    /**
     * 是否将类中全部字段标记为Depend，默认为false
     * */
    boolean allDepend() default false;

    /** 当全部标记为@Depend的时候，此参数为所有字段标记的@Depend注解对象，默认为无参注解 */
    Depend depend() default @Depend;


    /** 根据参数类型列表来指定构造函数，默认为无参构造 */
    Class[] constructor() default {};

}
