package com.forte.qqrobot.anno.data;

/**
 * 注解的参数类
 * @see com.forte.qqrobot.anno.Catch 此注解的参数封装类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Catch {

    private final Class<? extends Throwable> value;
    private final int level;

    //**************** 默认值 ****************//

    private static final Class<? extends Throwable> DEFAULT_VALUE = java.lang.Exception.class;
    private static final int DEFAULT_LEVEL = 0;

    private static final Catch DEFAULT = new Catch(
            DEFAULT_VALUE,
            DEFAULT_LEVEL
    );

    private Catch(Class<? extends Throwable> value, int level) {
        this.value = value;
        this.level = level;
    }

    public static Catch build(Class<? extends Throwable> value, int level){
        return new Catch(value, level);
    }

    public static Catch build(com.forte.qqrobot.anno.Catch catchAnnotation){
        return build(
                catchAnnotation.value(),
                catchAnnotation.level()
        );
    }

    public static Catch build(){
        return DEFAULT;
    }

    public Class<? extends Throwable> value(){
        return value;
    }

    public int level(){
        return level;
    }

}
