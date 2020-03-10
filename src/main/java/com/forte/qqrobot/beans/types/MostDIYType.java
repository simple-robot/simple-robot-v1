package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.anno.data.Filter;
import com.forte.qqrobot.beans.function.MostDIYTypeFilter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.Filterable;
import com.forte.qqrobot.listener.ListenContext;
import com.forte.qqrobot.listener.invoker.AtDetection;

/**
 * 此枚举定义如果存在多个自定义规则，则如果进行匹配
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public enum MostDIYType {
    /** 需要全部匹配 */
    EVERY_MATCH((f, msg, at, context, filters) -> {
        boolean b = true;
        for (Filterable filter : filters) {
            b &= filter.filter(f, msg, at, context);
        }
        return b;
    }),
    /** 任意一个匹配 */
    ANY_MATCH((f, msg, at, context, filters) -> {
        for (Filterable filter : filters) {
            if(filter.filter(f, msg, at, context)){
                return true;
            }
        }
        return false;
    }),
    /** 没有匹配 */
    NONE_MATCH((f, msg, at, context, filters) -> {
        for (Filterable filter : filters) {
            if(filter.filter(f, msg, at, context)){
                return false;
            }
        }
        return true;
    })
    ;

    /**
     * 过滤器
     */
    private final MostDIYTypeFilter filter;


    public boolean test(Filter filter, MsgGet msgGet, AtDetection at, ListenContext context, Filterable[] filters){
        return this.filter.test(filter, msgGet, at, context, filters);
    }

    /**
     * 构造
     */
    MostDIYType(MostDIYTypeFilter mostDIYTypeFilter){
        this.filter = mostDIYTypeFilter;
    }

}
