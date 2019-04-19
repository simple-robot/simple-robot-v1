package com.forte.qqrobot.beans.messages.msgget;

/**
 * 群添加请求事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupAddRequest extends EventGet {

    /** 获取群号 */
    String getGroup();

    /** 获取QQ号 */
    String getQQ();

    /** 获取消息 */
    @Override
    String getMsg();

    /** 请求类消息的标识 */
    String getFlag();

}
