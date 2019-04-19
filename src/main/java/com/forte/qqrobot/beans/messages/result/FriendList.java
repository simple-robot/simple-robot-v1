package com.forte.qqrobot.beans.messages.result;

import java.util.Map;

/**
 * 好友列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface FriendList extends InfoResult {

    /*
        有分组比较特殊，暂不使用InfoResultList接口
     */

    /** 由于有好友分组信息，于是使用Map来区分 */
    Map<String, Friend[]> getFriendList();

    /** 通过某个分组获取其中的好友列表 */
    Friend[] getFirendList(String group);

    /**
     * 好友信息
     */
    interface Friend extends ResultInner{
        /** 获取好友昵称 */
        String getName();

        /** 获取好友QQ号 */
        String getQQ();
    }
}
