package com.forte.qqrobot.utils;

import com.forte.qqrobot.exception.DefaultHttpClientException;
import com.forte.qqrobot.exception.HttpResponseException;
import com.forte.qqrobot.sender.HttpClientAble;
import com.forte.qqrobot.system.CoreSystem;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认的HttpClient模板
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class DefaultHttpClientTemplate implements HttpClientAble {

    public static final String TEMP_NAME = "DEFAULT_HTTP_CLIENT";

    /**
     * utf-8字符编码
     */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    /**
     * 全局cookies, 当没有自定义cookie的时候使用此cookie
     */
    private static CookieStore baseCookieStore = new BasicCookieStore();

    /**
     * 连接管理器
     */
    private static PoolingHttpClientConnectionManager pool;

    /**
     * 请求配置
     */
    private static RequestConfig requestConfig;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());

            // 配置同时支持 HTTP 和 HTTPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();

            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 最大连接数, CPU数量的2倍
            pool.setMaxTotal(CoreSystem.getCpuCore() << 1);
            // 设置最大路由, CPU数量的2倍
            pool.setDefaultMaxPerRoute(CoreSystem.getCpuCore() << 1);


            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 5000;
            int connectTimeout = 5000;
            int connectionRequestTimeout = 5000;
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new DefaultHttpClientException(e);
        }
        // 设置请求超时时间
        int timeout = 5000;
        requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).build();
    }

    /**
     * 获取Http连接
     */
    private CloseableHttpClient getHttpClient() {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        return HttpClientBuilder.create()
                .setConnectionManager(pool)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(baseCookieStore)
                .build();
    }

    /**
     * 获取Http连接
     *
     * @param cookieStore cookie域
     */
    private CloseableHttpClient getHttpClient(CookieStore cookieStore) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(pool)
                .setDefaultRequestConfig(requestConfig);
        if (cookieStore != null) {
            httpClientBuilder.setDefaultCookieStore(cookieStore);
        }else{
            httpClientBuilder.setDefaultCookieStore(baseCookieStore);
        }
        return httpClientBuilder.build();
    }

    /**
     * 获取全局CookieStore
     */
    public static CookieStore getBaseCookieStore() {
        return baseCookieStore;
    }

    /**
     * 添加请求头
     *
     * @param httpRequest 请求体
     * @param header      请求头
     */
    private static void addHeaders(HttpRequest httpRequest, Map<String, String> header) {
        if (!header.isEmpty()) {
            header.forEach(httpRequest::addHeader);
        }
    }

    /**
     * 根据路径与参数获取URI对象
     *
     * @param url    请求路径
     * @param params 参数列表
     * @return
     * @throws URISyntaxException
     */
    private static URI getURI(String url, Map<String, String> params) throws URISyntaxException {
        // 参数
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!params.isEmpty()) {
            params.forEach(uriBuilder::addParameter);
        }
        return uriBuilder.setCharset(CHARSET_UTF_8).build();
    }

    /**
     * map转化为cookieStore
     *
     * @param cookies cookies
     */
    private static CookieStore toCookieStore(Map<String, String> cookies) {
        CookieStore cookieStore = new BasicCookieStore();
        cookies.entrySet().stream()
                .map(e -> new BasicClientCookie(e.getKey(), e.getValue()))
                .forEach(cookieStore::addCookie);
        return cookieStore;
    }

    /**
     * map转化为Cookie，并且添加额外的cookie
     *
     * @param cookies     cookies
     * @param cookieStore cookieStore
     */
    private static CookieStore toCookieStore(String domain, Map<String, String> cookies, CookieStore cookieStore) {
        CookieStore newCookieStore = new BasicCookieStore();
        // 添加旧的
        newCookieStore.getCookies().forEach(cookieStore::addCookie);
        // 添加新的
        cookies.entrySet().stream()
                .map(e -> {
                    final BasicClientCookie cookie = new BasicClientCookie(e.getKey(), e.getValue());
                    cookie.setPath("/");
                    cookie.setDomain(domain);
                    return cookie;
                })
                .forEach(newCookieStore::addCookie);
        return newCookieStore;
    }

    /**
     * map转化为Cookie，并且添加额外的cookie
     *
     * @param cookies     cookies
     * @param cookieStore cookieStore
     */
    private static CookieStore toCookieStore(Map<String, String> cookies, CookieStore cookieStore) {
        return toCookieStore(".", cookies, cookieStore);
    }

    /**
     * 发送一个请求
     *
     * @param httpClient 连接
     * @param request    请求体
     * @return
     * @throws IOException
     */
    private static String send(CloseableHttpClient httpClient, HttpUriRequest request) throws IOException {
        // 请求并获取响应值
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // 判断响应值是否为300以下
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode >= 300) {
                String reasonPhrase = statusLine.getReasonPhrase();
                throw new HttpResponseException("code", statusCode, reasonPhrase);
            }

            // 获取响应值
            HttpEntity entity = response.getEntity();
            String entityString = EntityUtils.toString(entity, CHARSET_UTF_8);
            EntityUtils.consume(entity);

            return entityString;
        }
    }


    /**
     * 使用get的方式进行网络请求
     *
     * @param url     送信网络路径
     * @param params  参数列表，默认为空map，可以为null
     * @param cookies 所携带的cookie列表，默认为空map，可以为null
     * @param header  头信息，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    @Override
    public String get(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> header) {
        params = params == null ? new HashMap<>(1) : params;
        cookies = cookies == null ? new HashMap<>(1) : cookies;
        header = header == null ? new HashMap<>(1) : header;
        if(!header.containsKey(USER_AGENT_KEY_NAME)){
            header.put(USER_AGENT_KEY_NAME, USER_AGENT_WIN10_CHROME);
        }


        String response = null;
        try {
            // 创建URI
            URI uri = getURI(url, params);

            // 创建Get请求
            HttpGet httpGet = new HttpGet(uri);
            // 获取domain
            final String domain = httpGet.getURI().getAuthority();
            // 设置cookie
            CookieStore cookieStore;
            if (!cookies.isEmpty()) {
                cookieStore = toCookieStore(domain, cookies, baseCookieStore);
            } else {
                cookieStore = baseCookieStore;
            }

            // 设置请求头
            addHeaders(httpGet, header);

            // 发送请求
            CloseableHttpClient httpClient = getHttpClient(cookieStore);
            response = send(httpClient, httpGet);
        } catch (Exception e) {
            throw new DefaultHttpClientException(e);
        }
        return response;
    }


    /**
     * 使用post的方式进行网络请求
     * 一般header中会提供一些json或者from的参数
     *
     * @param url     送信网络路径
     * @param params  参数列表，默认为空map，可以为null
     * @param cookies 所携带的cookie列表，默认为空map，可以为null
     * @param header  头信息，默认为空map，可以为null
     * @return 网页的返回值字符串
     */
    @Override
    public String post(String url, String params, Map<String, String> cookies, Map<String, String> header) {
        cookies = cookies == null ? new HashMap<>(1) : cookies;
        header = header == null ? new HashMap<>(1) : header;
        String response = null;

        try {
            // 创建Post请求
            HttpPost httpPost = new HttpPost(url);
            // 获取domain
            final String domain = httpPost.getURI().getAuthority();

            // 设置cookie
            CookieStore cookieStore;
            if (!cookies.isEmpty()) {
                cookieStore = toCookieStore(domain, cookies, baseCookieStore);
            } else {
                cookieStore = baseCookieStore;
            }

            // 存在参数, 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity entity = new StringEntity(params, CHARSET_UTF_8);
                httpPost.setEntity(entity);
            }


            // 添加头信息
            addHeaders(httpPost, header);

            // 发送请求
            CloseableHttpClient httpClient = getHttpClient(cookieStore);
            response = send(httpClient, httpPost);
        } catch (Exception e) {
            throw new DefaultHttpClientException(e);
        }
        return response;
    }


}
