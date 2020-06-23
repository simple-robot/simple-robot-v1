package com.forte.qqrobot.beans.messages;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface NickOrRemark extends NicknameAble, RemarkAble {

    /**
     * 尝试获取备注，如果没用备注则获取昵称
     * @return 备注，或者昵称
     */
    default String getRemarkOrNickname(){
        final String remark = getRemark();
        return remark == null ? getNickname() : remark;
    }
}
