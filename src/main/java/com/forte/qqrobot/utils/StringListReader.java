/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     StringListReader.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将字符串集合转化为Reader对象
 * 未经测试可能存在BUG
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringListReader extends Reader {

    private final List<Character> charArr;

    /** 当前所在索引,默认从0开始 */
    private int index = DEFAULT_INDEX;

    /** 索引的默认初始值，一般为0 */
    private static final int DEFAULT_INDEX = 0;

    /**
     * Reads characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * @param cbuf Destination buffer
     * @param off  Offset at which to start storing characters
     * @param len  Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     * @throws IOException If an I/O error occurs
     */
    @Override
    public int read(char[] cbuf, int off, int len) {
        if(charArr.size() <= 0){
            return -1;
        }

        if(index > charArr.size() - 1){
            return -1;
        }

        //已经记录过的数量
        int nums = 0;

        //开始索引应该是数组的开始索引
        for (int i = 0; i < len; i++) {
            if(index > charArr.size() - 1){
                return nums;
            }
            cbuf[off+i] = charArr.get(index++);
            nums++;
        }

        return nums;
    }

    /**
     * 此流close的时候并不会关闭而是将索引值恢复到0的位置，毕竟本质只是个list集合所以无伤大雅（大概
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() {
        index = DEFAULT_INDEX;
    }

    /**
     * Creates a new character-stream reader whose critical sections will
     * synchronize on the reader itself.
     */
    public StringListReader(List<String> list) {
        //使用properties接收参数，则将字符串集合转化为Reader流对象
        this.charArr = list.stream().flatMap(s -> {
            s = s.endsWith("\n") ? s : s + "\n";
            char[] chars = s.toCharArray();
            Character[] arr = new Character[chars.length];
            for (int i = 0; i < chars.length; i++) {
                arr[i] = chars[i];
            }
            return Arrays.stream(arr);
        }).collect(Collectors.toList());
    }



}
