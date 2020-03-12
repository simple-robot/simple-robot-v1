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

    public void setDefaultMsgSender(MsgSender defaultMsgSender) {
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
