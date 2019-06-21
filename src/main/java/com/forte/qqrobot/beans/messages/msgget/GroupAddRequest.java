package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;

/**
 * 群添加请求事件
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupAddRequest extends EventGet, CodesAble {

    /** 获取群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 获取QQ号 */
    String getQQ();

    @Override
    default String getQQCode(){
        return getQQ();
    }

    /** 获取消息 */
    @Override
    String getMsg();

    /** 请求类消息的标识 */
    String getFlag();

}
