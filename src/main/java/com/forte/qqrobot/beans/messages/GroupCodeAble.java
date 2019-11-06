package com.forte.qqrobot.beans.messages;

/**
 * 此接口定义那些存在群号的消息类型的获取群号的方法
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupCodeAble {

    /**
     * 获取消息中存在的群号信息
     */
    String getGroupCode();

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
