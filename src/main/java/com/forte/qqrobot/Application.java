package com.forte.qqrobot;

import com.forte.forlemoc.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 用户实现的启动器的接口
 * 泛型定义:
 * - 配置类的具体类型
 * 定义启动类执行前后方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/29 10:16
 * @since JDK1.8
 **/
public interface Application<CONFIG extends BaseConfiguration> {

    void before(CONFIG configuration);

    void after(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);

    default String author(){
        return "@ForteScarlet";
    }

}
