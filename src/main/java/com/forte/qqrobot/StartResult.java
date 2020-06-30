/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     StartResult.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot;

import com.forte.qqrobot.sender.MsgSender;

/** BaseApplication中，执行start方法后的结果值
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
class StartResult<SEND, SET, GET> {

    private String startName;
    private MsgSender defaultMsgSender;
    private MsgProcessor msgProcessor;
    private MsgParser msgParser;

    StartResult(String startName, MsgSender defaultMsgSender, MsgProcessor msgProcessor, MsgParser msgParser) {
        this.startName = startName;
        this.defaultMsgSender = defaultMsgSender;
        this.msgProcessor = msgProcessor;
        this.msgParser = msgParser;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public MsgSender getDefaultMsgSender() {
        return defaultMsgSender;
    }

    public void setDefaultMsgSender(DefaultBotSender defaultMsgSender) {
        this.defaultMsgSender = defaultMsgSender;
    }

    public MsgProcessor getMsgProcessor() {
        return msgProcessor;
    }

    public void setMsgProcessor(MsgProcessor msgProcessor) {
        this.msgProcessor = msgProcessor;
    }

    public MsgParser getMsgParser() {
        return msgParser;
    }

    public void setMsgParser(MsgParser msgParser) {
        this.msgParser = msgParser;
    }
}
