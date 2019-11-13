package com.forte.qqrobot.log;

import com.forte.plusutils.consoleplus.FortePlusPrintStream;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import com.forte.plusutils.consoleplus.console.colors.BackGroundColorTypes;
import com.forte.plusutils.consoleplus.console.colors.ColorTypes;
import com.forte.plusutils.consoleplus.console.colors.FontColorTypes;
import com.forte.plusutils.consoleplus.system.ColorSystem;
import com.forte.qqrobot.utils.RandomUtil;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * log类, 继承ColorSystem，彩色打印
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:34
 * @since JDK1.8
 **/
public class QQLog extends ColorSystem {

    private static final PrintStream warning;

    /**
     * 日志的连接信息
     */
    private static QQLogBack qqLogBack = (m, l, e) -> true;

    static {
        //设置err为红色字体
        setErrTextFunction(str -> Colors.builder().add(str, Colors.FONT.RED).build().toString());
        //设置警告类型输出
        PrintStream warningPrintStream;
        try {
            warningPrintStream = FortePlusPrintStream.getInstance(System.out, obj -> {
                Colors timeColors = Colors.builder().add("[" + LocalDateTime.now().toString() + "]", Colors.FONT.BLUE).build();
                Colors typeColors = Colors.builder()
                        .add("[", Colors.FONT.BLUE)
                        .add("WARN", Colors.FONT.YELLOW)
                        .add("]", Colors.FONT.BLUE)
                        .build();
                return timeColors + " " + typeColors + " " + obj;
            });
        } catch (IllegalAccessException | UnsupportedEncodingException e) {
            warningPrintStream = null;
        }
        warning = warningPrintStream;

        String oh_hi_is_me = "_(^w^)L~~ by simple-robot@ForteScarlet ~~";
        int length = oh_hi_is_me.length();
        char line = '~';
        /* QQLog初始化的时候输出个东西~ */
        ColorsBuilder hi_i_am_builder_HEAD = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_HEAD.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_HEAD.build().toString());

        ColorsBuilder hi_i_am_builder = Colors.builder();
        oh_hi_is_me.chars().forEach(ic -> hi_i_am_builder.add((char) ic, wowThatIsRainbow$()));
        System.out.println(hi_i_am_builder.build().toString());
        ColorsBuilder hi_i_am_builder_END = Colors.builder();
        for (int i = 0; i < length; i++) {
            hi_i_am_builder_END.add(line, wowThatIsRainbowToo$());
        }

        System.out.println(hi_i_am_builder_END.build().toString());
    }

    private static ColorTypes wowThatIsRainbow$(){
        return RandomUtil.getRandomElement(FontColorTypes.values());
    }
    private static ColorTypes wowThatIsRainbowToo$(){
        return RandomUtil.getRandomElement(BackGroundColorTypes.values());
    }

    /**
     * 更换日志阻断
     */
    public static void changeQQLogBack(QQLogBack logBack) {
        QQLog.qqLogBack = logBack;
    }

    public static void info(Object msg) {
        if (qqLogBack.onLog(msg, LogLevel.INFO))
            info.println(msg);
    }

    public static void debug(Object msg) {
        if (qqLogBack.onLog(msg, LogLevel.DEBUG))
            debug.println(msg);
    }

    public static void error(Object msg, Throwable e) {
        if (qqLogBack.onLog(msg, LogLevel.ERROR, e)) {
            err.println(msg);
            if (e != null)
                e.printStackTrace();
        }
    }

    public static void warning(Object msg) {
        if (qqLogBack.onLog(msg, LogLevel.WARNING))
            warning.println(msg);
    }


}
