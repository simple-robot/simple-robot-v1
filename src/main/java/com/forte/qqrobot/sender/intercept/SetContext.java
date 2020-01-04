package com.forte.qqrobot.sender.intercept;

import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SetContext extends SenderContext<SenderSetList> {

    public final SenderSetList SETTER;

    /** set所使用的全局Map */
    private static final Map<String, Object> GLOBAL_CONTEXT_MAP = new ConcurrentHashMap<>(4);

    public SetContext(SenderSetList value, Object... params) {
        super(value, GLOBAL_CONTEXT_MAP, params);
        SETTER = value;
    }
}
