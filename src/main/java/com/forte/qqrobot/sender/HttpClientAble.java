/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-core
 * File     HttpClientAble.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 */

package com.forte.qqrobot.sender;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供可以进行Http送信的接口模板
 * 此模板只提供两个最常用的送信方式：get、post
 * 其最终结果将会被扫描并注册进
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public interface HttpClientAble {

    /**
     * HTTP内容类型。 text格式
     */
    String CONTENT_TYPE_TEXT_XML = "text/xml";
    /**
     * HTTP内容类型。from格式，提交数据
     */
    String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    /**
     * HTTP内容类型。json格式，提交数据
     */
    String CONTENT_TYPE_JSON = "application/json;charset=utf-8";

    /** application/json */
    String APPLICATION_JSON = "application/json";

    String USER_AGENT_KEY_NAME = "User-Agent";

    String USER_AGENT_WIN10_CHROME = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

    String USER_AGENT_MAC_FIREFOX = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";

    /**
     * 使用get的方式进行网络请求
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map，可以为null
     * @param cookies   所携带的cookie列表，默认为空map，可以为null
     * @param header    头信息，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    String get(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> header);

    /**
     * 使用get的方式进行网络请求
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map，可以为null
     * @param cookies   所携带的cookie列表，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    default String get(String url, Map<String, String> params, Map<String, String> cookies){
        return get(url, params, cookies, null);
    }

    /**
     * 使用get的方式进行网络请求
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    default String get(String url, Map<String, String> params){
        return get(url, params, null, null);
    }

    /**
     * 使用get的方式进行网络请求
     * @param url       送信网络路径
     * @return 网页的返回值字符串
     */
    default String get(String url){
        return get(url, null, null,null);
    }

    /**
     * 使用post的方式进行网络请求
     * 一般header中会提供一些json或者from的参数
     * @param url       送信网络路径
     * @param params    参数列表，默认为空map，可以为null
     * @param cookies   所携带的cookie列表，默认为空map，可以为null
     * @param header    头信息，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    String post(String url, String params, Map<String, String> cookies, Map<String, String> header);


    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @param cookies   cookies
     * @param header    头信息
     * @return 响应结果
     */
    default String postJson(String url, String params, Map<String, String> cookies, Map<String, String> header){
        header.putIfAbsent("Content-type", CONTENT_TYPE_JSON);
        return post(url, params, cookies, header);
    }
    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串，可以为null
     * @param cookies   cookies
     * @return 响应结果
     */
    default String postJson(String url, String params, Map<String, String> cookies){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_JSON);
        return post(url, params, cookies, header);
    }
    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串，可以为null
     * @return 响应结果
     */
    default String postJson(String url, String params){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_JSON);
        return post(url, params, null, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @return 响应结果
     */
    default String postJson(String url){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_JSON);
        return post(url, null, null, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @param cookies   cookies
     * @param header    头信息
     * @return 响应结果
     */
    default String postForm(String url, String params, Map<String, String> cookies, Map<String, String> header){
        header.putIfAbsent("Content-type", CONTENT_TYPE_FORM);
        return post(url, params, cookies, header);
    }


    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @param cookies   cookies
     * @return 响应结果
     */
    default String postForm(String url, String params, Map<String, String> cookies){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_FORM);
        return post(url, params, cookies, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @return 响应结果
     */
    default String postForm(String url, String params){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_FORM);
        return post(url, params, null, header);
    }


    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @return 响应结果
     */
    default String postForm(String url){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_FORM);
        return post(url, null, null, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @param cookies   cookies
     * @param header    头信息
     * @return 响应结果
     */
    default String postXml(String url, String params, Map<String, String> cookies, Map<String, String> header){
        header.putIfAbsent("Content-type", CONTENT_TYPE_TEXT_XML);
        return post(url, params, cookies, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @param cookies   cookies
     * @return 响应结果
     */
    default String postXml(String url, String params, Map<String, String> cookies){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_TEXT_XML);
        return post(url, params, cookies, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @param params    参数字符串
     * @return 响应结果
     */
    default String postXml(String url, String params){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_TEXT_XML);
        return post(url, params, null, header);
    }

    /**
     * <pre> json格式post
     * <pre> 会向参数header中添加头信息：
     * <pre> Content-type, application/json; charset=utf-8
     * <pre> Accept, application/json
     * @param url       请求路径
     * @return 响应结果
     */
    default String postXml(String url){
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-type", CONTENT_TYPE_TEXT_XML);
        return post(url, null, null, header);
    }

}
