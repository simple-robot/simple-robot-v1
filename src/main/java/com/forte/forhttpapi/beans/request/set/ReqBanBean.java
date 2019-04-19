package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * 禁言相关的接口，定义了最大禁言时间
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:49
 * @since JDK1.8
 **/
public interface ReqBanBean extends ReqBean {

    /** 最大禁言时间 */
    Long MAX_BAN_TIME = 2592000L - 1;

    /** 最小禁言时间，即解除禁言 */
    Long MIN_BAN_TIME = 0L;

}
