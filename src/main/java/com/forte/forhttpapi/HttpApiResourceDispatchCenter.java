package com.forte.forhttpapi;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.utils.SingleFactory;

/**
 * HTTP API使用的资源调度器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/15 17:15
 * @since JDK1.8
 **/
public class HttpApiResourceDispatchCenter extends ResourceDispatchCenter {

    /**
     * 保存一个Http配置类
     * @param httpConfiguration Http配置类
     */
    static void saveHttpConfiguration(HttpConfiguration httpConfiguration){
        ResourceDispatchCenter.saveConfiguration(httpConfiguration);
    }

    /**
     * 获取一个HttpConfiguration单例对象
     * @return HttpConfiguration单例对象
     */
    public static HttpConfiguration getHttpConfiguration(){
        return SingleFactory.get(HttpConfiguration.class);
    }


}
