package com.forte.qqrobot.beans.messages;

/**
 * 此接口规定方法以获取那些存在<code>QQ号</code>的信息的QQ号
 * 用于接收的消息或事件中
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface QQCodeAble {

    /**
     * 获取QQ号信息。
     * 假如一个消息封装中存在多个QQ号信息，例如同时存在处理者与被处理者，一般情况下我们认为其返回值为被处理者。
     */
    String getQQCode();

    /**
     * 将{@link #getQQCode()} ()} 获取到的值转化为long类型
     * @return long类型的qq号
     * @throws NumberFormatException 存在数字转化隐患
     */
    default Long getQQCodeNumber(){
        String qqCode = getQQCode();
        if(qqCode == null || qqCode.trim().length() == 0){
            return 0L;
        }else{
            return Long.parseLong(qqCode);
        }
    }

}
