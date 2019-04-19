package com.forte.qqrobot;

import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 启动器的接口
 * 泛型定义配置类的具体类型
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:16
 * @since JDK1.8
 **/
public interface SimpleApplication<T extends BaseConfiguration> {

    void before(T configuration);

    void after(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);

    default String author(){
        return "@ForteScarlet";
    }

}
