package com.forte.qqrobot.sender;

import java.util.Map;

/**
 * 提供可以进行Http送信的接口模板
 * 此模板只提供两个最常用的送信方式：get、post
 * 其最终结果将会被扫描并注册进
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface HttpClientAble {



    /**
     * 使用get的方式进行网络请求
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map
     * @param cookies   所携带的cookie列表，默认为空map
     * @param header    头信息，默认为空map
     * @return 网页的返回值字符串
     */
    String get(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> header);

    /**
     * 使用post的方式进行网络请求
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map
     * @param cookies   所携带的cookie列表，默认为空map
     * @param header    头信息，默认为空map
     * @return 网页的返回值字符串
     */
    String post(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> header);






}
