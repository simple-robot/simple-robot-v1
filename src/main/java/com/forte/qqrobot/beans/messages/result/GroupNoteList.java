package com.forte.qqrobot.beans.messages.result;

/**
 * 群公告列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupNoteList extends InfoResultList<GroupNoteList.GroupNote> {

    /** 获取群公告列表 */
//    GroupNote[] getGroupNoteList();

    /**
     * 群公告
     */
    interface GroupNote extends ResultInner{
        /** ID */
        String getId();
        /** 完整正文 */
        String getMsg();
        /** 预览文 */
        String getFaceMsg();
        /** 标题 */
        String getTitle();
        /** 发布时间 */
        Long getTime();
        /** 已读人数数量 */
        Integer getReadNum();

        /** 是否提醒群员修改群名片 */
        Boolean isShowEditCard();

        /** 发布者QQ */
        String getQQ();
        /** 公告类型ID */
        String getTypeId();

    }
}
