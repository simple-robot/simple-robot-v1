package com.forte.qqrobot.beans.messages.msgget;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.types.IncreaseType;

/**
 * 群成员增加
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 **/
public interface GroupMemberIncrease extends EventGet, CodesAble {

    /** 获取类型 */
    IncreaseType getType();

    /** 群号 */
    String getGroup();

    @Override
    default String getGroupCode(){
        return getGroup();
    }

    /** 操作者的QQ号 */
    String getOperatorQQ();

    /** 被操作者的QQ号 */
    String getBeOperatedQQ();

    /** 使用的是被操作者的QQ */
    @Override
    default String getQQCode(){
        return getBeOperatedQQ();
    }


}
