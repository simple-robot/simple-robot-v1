package com.forte.qqrobot.depend.parameter;

import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.log.QQLog;

/**
 * 此类提供一个静态方法，来获取一个参数获取器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public class ParamGetterManager {

    private static final String PARAM_NAME = "name";

    private static final ParamNameGetter PARAM_NAME_GETTER;

    /*
     * 此方法并行不通，考虑其他方法。
     */
    static {
        ParamNameGetter paramNameGetter;
        try {
            String method = ParamGetterManager.class.getDeclaredMethod("method", String.class)
                    .getParameters()[0].getName();

            if(method.equals(PARAM_NAME)){
                //如果能获取参数
                paramNameGetter = new NormalParamNameGetter();
            }else{
                paramNameGetter = new NullParamNameGetter();
                info();

            }
        } catch (NoSuchMethodException e) {
            paramNameGetter = new NullParamNameGetter();
            info();
        }
        //赋值
        PARAM_NAME_GETTER = paramNameGetter;
    }


    /**
     * 此方法仅用作检测对是否能获取参数名称
     */
    private static void method(String name){}

    private static void info(){
        Colors info = Colors.builder()
                .add("\n")
                .add("检测到无法正常获取方法的参数名！\n" +
                "假如您是maven项目，" +
                "请在build标签中添加一个compile插件：\n")
                .add("(※标注了", Colors.FONT.YELLOW)
                .add("红色", Colors.FONT.RED)
                .add("和", Colors.FONT.YELLOW)
                .add("蓝色", Colors.FONT.BLUE)
                .add("的为所需要的关键属性)\n")
                .add("(※如果您使用的编译器为Eclipse且无法正常识别彩色字体，请安装eclipse的插件ANSI Escape in Console)\n" +
                        "(※下载地址：https://download.csdn.net/download/leiyong0326/10112547)\n" +
                        "请在build标签中添加一个plugin标签：\n" +
                        "<plugin>\n" +
                        "<groupId>org.apache.maven.plugins</groupId>\n" +
                        "<artifactId>maven-compiler-plugin</artifactId>\n" +
                        "<version>#{版本, 例如3.7.0}</version>\n" +
                        "<configuration>\n" +
                        "<!-- java版本 -->\n" +
                        "    <source>1.8</source>\n" +
                        "    <target>1.8</target>\n")
                .color(Colors.FONT.YELLOW)
                .add("    <compilerArgs>\n")
                .add("        <arg>", Colors.FONT.BLUE)
                .add("-parameters", Colors.FONT.RED)
                .add("</arg>\n", Colors.FONT.BLUE)
                .add("    </compilerArgs>\n", Colors.FONT.BLUE)
                .add("    </configuration>\n")
                .add("</plugin>\n")
                .add("\n")
                .add("假如您不是maven项目，请查询使用您的编译器在java8中添加编译参数-parameters的方法。\n")
                .color(Colors.FONT.YELLOW)
                .build();
        QQLog.info(info.toString());
    }

    /**
     * 获取参数解析器
     */
    public static ParamNameGetter getParamNameGetter(){
        return PARAM_NAME_GETTER;
    }

}
