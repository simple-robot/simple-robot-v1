package com.forte.qqrobot.beans.messages.result;

/**
 * 群链接地址
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupLinkList extends InfoResultList<GroupLinkList.GroupLink> {

    /** 获取群链接列表 */
//    GroupLink[] getGroupLinkList();

    /**
     * 群链接
     */
    interface GroupLink extends ResultInner{
        /** 链接地址 */
        String getUrl();
        /** 链接的封面地址 */
        String getPicUrl();
        /** 发布时间 */
        Long getTime();
        /** 标题 */
        String getTitle();
        /** 该连接的发布者QQ */
        String getQQ();
    }
}
