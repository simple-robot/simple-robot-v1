package com.forte.qqrobot.listener.error;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.sender.MsgSender;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ExceptionHandleContextImpl implements ExceptionHandleContext {
    private String id;
    private MsgGet msgGet;
    private MsgSender msgSender;
    private Exception exception;

    public ExceptionHandleContextImpl(String id, MsgGet msgGet, MsgSender msgSender, Exception exception) {
        this.id = id;
        this.msgGet = msgGet;
        this.msgSender = msgSender;
        this.exception = exception;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public MsgGet getMsgGet() {
        return msgGet;
    }

    public void setMsgGet(MsgGet msgGet) {
        this.msgGet = msgGet;
    }

    @Override
    public MsgSender getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
