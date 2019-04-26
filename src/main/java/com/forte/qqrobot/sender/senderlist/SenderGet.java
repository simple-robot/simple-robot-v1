package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.get.InfoGet;
import com.forte.qqrobot.beans.messages.result.InfoResult;

import java.util.Map;

/**
 * 提供1个方法以汇总Getter方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderGet {


    /**
     * 通过定义好返回值的InfoGet对象获取返回值
     * @param infoGet   InfoGet对象
     * @param <RESULT>  InfoGet对象的指定返回值类型
     * @return 响应数据封装类
     */
    <RESULT extends InfoResult> RESULT get(InfoGet<RESULT> infoGet);

}
