package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.result.inner.Friend;

import java.util.Map;

/**
 * 好友列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface FriendList extends InfoResult {

    /*
        好友列表有分组比较特殊，暂不使用InfoResultList接口
     */

    /** 各个分组下的好友列表 */
    Map<String, Friend[]> getFriendList();

    /** 通过某个分组获取其中的好友列表 */
    Friend[] getFirendList(String group);

}
