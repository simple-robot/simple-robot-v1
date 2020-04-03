package com.forte.qqrobot.sender;

import com.forte.qqrobot.exception.HttpClientHelperException;
import com.forte.qqrobot.utils.DefaultHttpClientTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 送信器帮助中心，启动时会扫描并注入实现了HttpClientAble接口的实现类并存入其中，使用户可以自由获取。
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class HttpClientHelper implements HttpClientAble {

    /**
     * 当前默认获取的client的name值。
     */
    private static volatile String defaultClientName;

    /**
     * 记录已经注册的client
     */
    private static final Map<String, Supplier<HttpClientAble>> clientMap = new ConcurrentHashMap<>(2);

    /**
     * 注册一个http模板
     * @param name          http模板名称
     * @param clientAble    模板实例
     */
    public static void registerClient(String name, HttpClientAble clientAble){
        registerClient(name, () -> clientAble);
    }

    /**
     * 注册一个http模板
     * @param name                  http模板名称
     * @param clientAbleSupplier    模板实例获取函数
     */
    public static synchronized void registerClient(String name, Supplier<HttpClientAble> clientAbleSupplier){
        clientMap.merge(name, clientAbleSupplier, (old, val) -> {
            throw new HttpClientHelperException("nameExists", name);
        });
        if(defaultClientName == null){
            setDefaultName(name);
        }
    }

    /**
     * 根据当前默认名称获取模板
     * @return 模板
     */
    public static HttpClientAble getDefaultHttp(){
        if(defaultClientName == null){
            final DefaultHttpClientTemplate defaultHttpClientTemplate = new DefaultHttpClientTemplate();
            registerClient("defaultHttpClientTemplate", defaultHttpClientTemplate);
            return defaultHttpClientTemplate;
        }
        return getHttp(defaultClientName);
    }

    /**
     * 获取一个http模板
     * @param name 名称
     * @return 模板
     */
    public static HttpClientAble getHttp(String name){
        Supplier<HttpClientAble> httpSupplier = clientMap.get(name);
        if(httpSupplier == null){
            throw new HttpClientHelperException("notExists", name);
        }else{
            return httpSupplier.get();
        }
    }

    /**
     * 切换默认的client名称
     * @param defaultName 默认名称
     */
    public static synchronized void setDefaultName(String defaultName){
        if(clientMap.containsKey(defaultName)){
            defaultClientName = defaultName;
        }else{
            throw new HttpClientHelperException("nameExists", defaultName);
        }
    }

    //**************** 此类本身也可以直接使用，默认为新建时默认http模板的使用 ****************//

    private HttpClientAble httpTemplate;

    public HttpClientHelper(){
        this.httpTemplate = getDefaultHttp();
    }

    /**
     * 使用get的方式进行网络请求
     *
     * @param url     送信网络路径
     * @param params  参数列表，默认为空map
     * @param cookies 所携带的cookie列表，默认为空map
     * @param header  头信息，默认为空map
     * @return 网页的返回值字符串
     */
    @Override
    public String get(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> header) {
        return httpTemplate.get(url, params, cookies, header);
    }

    /**
     * 使用post的方式进行网络请求
     *
     * @param url     送信网络路径
     * @param params  参数列表，默认为空map
     * @param cookies 所携带的cookie列表，默认为空map
     * @param header  头信息，默认为空map
     * @return 网页的返回值字符串
     */
    @Override
    public String post(String url, String params, Map<String, String> cookies, Map<String, String> header) {
        return httpTemplate.post(url, params, cookies, header);
    }

    public HttpClientAble getHttpTemplate() {
        return httpTemplate;
    }

    public void setHttpTemplate(HttpClientAble httpTemplate) {
        this.httpTemplate = httpTemplate;
    }
}
