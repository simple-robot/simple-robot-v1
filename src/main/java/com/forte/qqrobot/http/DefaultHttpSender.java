package com.forte.qqrobot.http;

import com.forte.qqrobot.SocketResourceDispatchCenter;
import com.forte.forhttpapi.beans.response.RespBean;
import com.forte.qqrobot.socket.MsgSender;
import com.forte.qqrobot.socket.QQJSONMsgCreator;

import java.util.Optional;

/**
 * 继承HttpSender，当没有使用HTTP API的时候{@link MsgSender}中将会保存此对象以处理空指针
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/30 11:16
 * @since JDK1.8
 **/
public class DefaultHttpSender extends QQHttpMsgSender {

    /**
     * 私有构造
     * @param creator
     */
    DefaultHttpSender(QQJSONMsgCreator creator) {
        super(creator);
    }

    /**
     * 构造
     */
    public DefaultHttpSender(){
        super(SocketResourceDispatchCenter.getQQJSONMsgCreator());
    }

    /**
     * 通过HTTP API插件获取所需消息
     * * 重写后，使其失去作用而只会返回null值
     * @param json     json参数字符串
     * @param beanType bean类型
     */
    @Override
    public <T extends RespBean> Optional<T> get(String json, Class<T> beanType) {
        return Optional.empty();
    }
}
