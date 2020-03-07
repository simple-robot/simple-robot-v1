package com.forte.qqrobot.beans.messages.result.inner;

import com.forte.qqrobot.beans.messages.result.ResultInner;

/**
 * 群列表的群信息
 */
public interface Group extends ResultInner {
    /** 群名 */
    String getName();
    /** 群号 */
    String getCode();
    /** 群头像地址, 默认情况下直接使用p.qlogo接口 */
    default String getHeadUrl(){
        String groupCode = getCode();
        return "http://p.qlogo.cn/gh/"+ groupCode +"/"+ groupCode +"/640";
    }
}
