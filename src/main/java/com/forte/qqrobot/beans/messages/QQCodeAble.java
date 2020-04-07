package com.forte.qqrobot.beans.messages;

/**
 * 此接口规定方法以获取那些存在<code>QQ号</code>的信息的QQ号
 * 用于接收的消息或事件中
 * TODO 在某个中版本更新中会更名为CodeAble
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface QQCodeAble {

    /**
     * 获取QQ号信息。
     * 假如一个消息封装中存在多个QQ号信息，例如同时存在处理者与被处理者，一般情况下我们认为其返回值为被处理者。
     * TODO 将会被{@link #getCode()} 所取代
     * @see #getCode()
     */
//    @Deprecated
    String getQQCode();

    /**
     * 获取账号编号。后续某版本更新会移除getQQCode与getQQ等方法名，并统一为getCode
     * @return code
     */
    default String getCode(){
        return getQQCode();
    }

    /**
     * 获取账号头像
     */
    default String getQQHeadUrl(){
        // 2020/4/8 http://q1.qlogo.cn/g?b=qq&nk=QQ号码&s=640
        final String code = getCode();
        if(code == null){
            return null;
        }
        return "http://q1.qlogo.cn/g?b=qq&nk="+ code +"&s=640";
    }

    /**
     * 将{@link #getQQCode()} ()} 获取到的值转化为long类型
     * @return long类型的qq号
     * @throws NumberFormatException 存在数字转化隐患
     * @see #getCodeNumber()
     */
    @Deprecated
    default Long getQQCodeNumber(){
        String qqCode = getQQCode();
        if(qqCode == null || qqCode.trim().length() == 0){
            return 0L;
        }else{
            return Long.parseLong(qqCode);
        }
    }
    /**
     * 将{@link #getCode()} ()} 获取到的值转化为long类型
     * @return long类型的qq号
     * @throws NumberFormatException 存在数字转化隐患
     */
    default Long getCodeNumber(){
        String qqCode = getQQCode();
        if(qqCode == null || qqCode.trim().length() == 0){
            return 0L;
        }else{
            return Long.parseLong(qqCode);
        }
    }

}
