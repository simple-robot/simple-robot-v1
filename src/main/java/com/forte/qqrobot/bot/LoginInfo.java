package com.forte.qqrobot.bot;

import com.forte.qqrobot.beans.messages.result.LoginQQInfo;

/**
 * 登录后的信息，为LoginQQInfo的子接口并计划替代LoginQQInfo接口
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface LoginInfo extends LoginQQInfo {

    @Override
    default String getCode(){
        return getQQ();
    }

}
