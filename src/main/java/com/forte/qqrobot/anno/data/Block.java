package com.forte.qqrobot.anno.data;

/**
 * 注解的参数类
 * @see com.forte.qqrobot.anno.Block 此注解的封装类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Block {

    private final String[] value;

    //**************** 默认值 ****************//

    private static final String[] DEFAULT_VALUE = {};

    private static final Block DEFAULT = new Block(DEFAULT_VALUE);

    private Block(String[] value) {
        this.value = value;
    }

    public static Block build(String[] value){
        return new Block(value);
    }

    public static Block build(com.forte.qqrobot.anno.Block blockAnnotation){
        return build(blockAnnotation.value());
    }

    public static Block build(){
        return DEFAULT;
    }

    public String[] value(){
        return value;
    }

}
