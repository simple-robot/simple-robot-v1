package com.forte.qqrobot.beans.messages.get;

import com.forte.qqrobot.beans.messages.result.GroupNoteList;

/**
 * 获取群公告列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetGroupNoteList extends InfoGet<GroupNoteList> {

    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @Override
    default Class<GroupNoteList> resultType(){
        return GroupNoteList.class;
    }

}
