package com.forte.qqrobot.listener.error;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.sender.MsgSender;

import java.util.Map;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ExceptionHandleContextImpl<T extends Exception> extends BaseContext<T> implements ExceptionHandleContext<T> {
    /** 监听函数的id */
    private String id;
    /** 接收到的消息 */
    private MsgGet msgGet;
    /** 使用的送信器 */
    private MsgSender msgSender;
    /** 得到的异常 */
    private T exception;
    /** 监听函数的排序值 */
    private int sort;

    public ExceptionHandleContextImpl(String id, MsgGet msgGet, int sort, MsgSender msgSender, Map<String, Object> globalMap, T exception) {
        super(exception, globalMap);
        this.id = id;
        this.msgGet = msgGet;
        this.sort = sort;
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
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public MsgSender getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    @Override
    public T getException() {
        return exception;
    }

    public void setException(T exception) {
        this.exception = exception;
    }
}
