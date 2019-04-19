package com.forte.qqrobot.beans.messages.result;

/**
 * 禁言列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface BanList extends InfoResultList<BanList.BanInfo> {

    /**
     * 获取禁言列表
     */
//    BanInfo[] getBanList();

    /**
     * 禁言详细信息
     */
    interface BanInfo extends ResultInner{
        /**
         * 禁言者的QQ
         */
        String getQQ();

        /**
         * 禁言成员昵称
         */
        String getNickName();

        /**
         * 是否为管理员
         */
        Boolean isManager();

        /**
         * 禁言剩余时间
         */
        Long lastTime();
    }
}
