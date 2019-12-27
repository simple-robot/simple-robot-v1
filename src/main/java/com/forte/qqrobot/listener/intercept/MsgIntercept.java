package com.forte.qqrobot.listener.intercept;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;

/**
 *
 * <p>消息拦截接口</p>
 * <p>消息拦截器在被内部使用的时候<b>仅会被实例化一次</b>，但是同样需要标记@Beans</p>
 * <p>当消息拦截器返回值为false的时候，会终止后续拦截器的执行与监听消息的监听。</p>
 * <p>排序使用正序排序，值越小优先级越高。</p>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MsgIntercept extends Interceptor<MsgGet, MsgGetContext>, Comparable<MsgIntercept> {

    /**
     * 排序方法，默认为1。
     * 无特殊排序需求则不用重写此方法。
     * @return 优先级，值越小优先级越高。目前理论上支持负值。
     */
    default int sort(){
        return 1;
    }

    /**
     * 实现的排序方法。
     * 无特许需求请不要重写此方法。
     * @return 排序值
     */
    @Override
    default int compareTo(MsgIntercept other){
        return Integer.compare(this.sort(), other.sort());
    }
}
