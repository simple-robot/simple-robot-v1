package com.forte.qqrobot.beans.inforeturn;

import com.forte.qqrobot.beans.MsgBean;

/**
 * 请求JSON数据的返回值
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 10:47
 * @since JDK1.8
 **/
public interface InfoReturn extends MsgBean {

    /** 事件编码, 固定为0 */
    Integer act = 0;

    @Override
    default Integer getAct(){
        return act;
    }

    Integer getReturn();
    Integer getError();

}
