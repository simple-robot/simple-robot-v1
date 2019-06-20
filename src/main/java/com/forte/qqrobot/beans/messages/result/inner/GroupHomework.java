package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 群作业信息
 */
public interface GroupHomework extends ResultInner {

    /**
     * 作业的内容信息
     */
    Content[] getContents(String key);

    /**
     * 科目ID
     */
    String getCourseId();

    /**
     * 科目名称
     */
    String getCourseName();

    /**
     * 该作业的科目图片链接
     */
    String getCoursePic();

    /**
     * 该作业ID
     */
    String getId();

    /**
     * 该作业标题
     */
    String getTitle();

    /**
     * 作业类型ID
     */
    String getType();

    /**
     * 该作业的图标链接
     */
    String getIcon();

    /**
     * 该作业是否需要反馈
     */
    Boolean isNeedFeedBack();

    /**
     * 发布人的昵称
     */
    String getAnnoNick();

    /**
     * 发布人的QQ号
     */
    String getAnnoQQ();

    /**
     * 作业的目前状态
     */
    String getStatus();

    /**
     * 创建时间
     */
    Long getTime();

}
