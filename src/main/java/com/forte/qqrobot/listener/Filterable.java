package com.forte.qqrobot.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.invoker.AtDetection;

/**
 * 使用户自己创建自定义的规则判断
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface Filterable {

    /**
     * 根据自定义规则对消息进行过滤
     * @param filter filter注解对象
     * @param msgget 接收到的消息
     * @param at     判断当前消息是否被at的函数
     * @return 是否通过
     */
    boolean filter(Filter filter, MsgGet msgget, AtDetection at);

}
