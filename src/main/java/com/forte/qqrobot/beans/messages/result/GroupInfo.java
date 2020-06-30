/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     GroupInfo.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.beans.messages.result;

import com.forte.qqrobot.beans.messages.GroupCodeAble;

import java.util.Map;

/**
 * 群详细信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GroupInfo extends InfoResult, GroupCodeAble {

    /** 群等级 */
    Integer getLevel();
    /** 加群方式 */
    Integer getOpenType();
    /** 群类型 */
    String getType();
    /** 群类型ID */
    Integer getTypeId();
    /** 获取群管理列表 */
    String[] getAdminList();
    /** 最新的公告 */
    String getBoard();
    /** 建群时间 */
    Long getCreateTime();
    /** 获取群介绍-简略 */
    String getSimpleIntro();
    /** 获取群介绍-完整 */
    String getCompleteIntro();
    /** 群成员上限 */
    Integer getMaxMember();
    /** 群成员数量 */
    Integer getMemberNum();
    /** 群名称 */
    String getName();
    /** 群主QQ号 */
    String getOwnerQQ();

    /** 群号 */
    String getCode();

    /** 等级信息 */
    Map<String, String> getLevelNames();

    /** 获取群主和管理的QQ与昵称列表 */
    Map<String, String> getAdminNickList();

    /** 获取群地址、坐标信息 */
    String getPos();

    /** 群搜索类型 */
    Integer getSearchType();

    /** 获取群标签 */
    String[] getTags();

    /** 群头像地址, 默认情况下直接使用p.qlogo接口 */
    default String getHeadUrl(){
        String groupCode = getCode();
        return "http://p.qlogo.cn/gh/"+ groupCode +"/"+ groupCode +"/640";
    }


    @Override
    default String getGroupCode(){
        return getCode();
    }

}
