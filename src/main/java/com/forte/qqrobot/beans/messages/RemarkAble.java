package com.forte.qqrobot.beans.messages;

/**
 * 可以获取备注信息。一般出现在好友信息或者群信息中
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface RemarkAble {

    /**
     * 获取备注信息，例如群备注，或者好友备注。
     * @return 备注信息
     */
    String getRemark();

}
