package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.get.GetGroupHomeworkList;

import java.util.Map;

/**
 * 群迆列表
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupHomeworkList extends InfoResultList<GroupHomeworkList.GroupHomework> {

    /** 获取群作业列表 */
//    GroupHomework[] GetGroupHomeworkList();

    /**
     * 群作业信息
     */
    interface GroupHomework extends ResultInner {

        /** 作业的内容信息 */
        Content[] getContents(String key);

        /** 科目ID */
        String getCourseId();
        /** 科目名称 */
        String getCourseName();
        /** 该作业的科目图片链接 */
        String getCoursePic();

        /** 该作业ID */
        String getId();
        /** 该作业标题 */
        String getTitle();
        /** 作业类型ID */
        String getType();
        /** 该作业的图标链接 */
        String getIcon();
        /** 该作业是否需要反馈 */
        Boolean isNeedFeedBack();
        /** 发布人的昵称 */
        String getAnnoNick();
        /** 发布人的QQ号 */
        String getAnnoQQ();
        /** 作业的目前状态 */
        String getStatus();

        /** 创建时间 */
        Long getTime();



        /**
         * 群作业信息
         */
        interface Content extends ResultInner{
            /** 获取内容 */
            String getText();
            /** 获取类型 */
            String getType();
        }
    }



}
