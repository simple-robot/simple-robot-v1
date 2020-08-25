/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupCodeAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages;

/**
 * 此接口定义那些存在群号的消息类型的获取群号的方法
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupCodeAble extends RemarkAble {

    /**
     * 获取消息中存在的群号信息
     */
    String getGroupCode();

    /**
     * 获取群头像的默认实现
     */
    default String getGroupHeadUrl(){
        final String groupCode = getGroupCode();
        if(groupCode == null){
            return null;
        }
        // 2020/4/8 http://p.qlogo.cn/gh/QQ群号码/群号码/640/
        return "http://p.qlogo.cn/gh/"+ groupCode +"/"+ groupCode +"/640/";
    }

    /**
     * 将{@link #getGroupCode()} 获取到的值转化为long类型
     * @return long类型的群号
     * @throws NumberFormatException 存在数字转化隐患
     */
    default Long getGroupCodeNumber(){
        String groupCode = getGroupCode();
        if(groupCode == null || groupCode.trim().length() == 0){
            return 0L;
        }else{
            return Long.parseLong(groupCode);
        }
    }


}
