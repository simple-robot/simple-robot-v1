package com.forte.qqrobot.sender;

import com.forte.qqrobot.beans.messages.result.AuthInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * 提供一些默认的API实现
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BaseAPITemplate {

    /**
     * 获取群详细信息
     * @param http http送信器
     * @param info 权限信息
     * @param groupCode 群号
     * @return
     */
    public static String getGroupInfo(HttpClientAble http, AuthInfo info, String groupCode){
        /*
        GET https://qinfo.clt.qq.com/cgi-bin/qun_info/get_group_info_v2 HTTP/1.1
        Host: qinfo.clt.qq.com
        Connection: keep-alive
        Accept: application/json
        User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) QQ/9.3.2.26849 Chrome/43.0.2357.134 Safari/537.36 QBCore/3.43.1298.400 QQBrowser/9.0.2524.400
        X-Requested-With: XMLHttpRequest
        Referer: https://qinfo.clt.qq.com/qinfo_v3/setting.html?groupuin=782930037
         */

        /*
        src=
        gc=782930037
        bkn=1730050886
         */
        String url = "https://qinfo.clt.qq.com/cgi-bin/qun_info/get_group_info_v2";

        final Map<String, String> cookies = getAuthCookies(info);

        // param list
        Map<String, String> ps = new HashMap<>(2);
        ps.put("src", "qinfo_v3");
        ps.put("gc", groupCode);
        ps.put("bkn", info.getCsrfToken());


        return http.get(url, ps, cookies);
    }

    /**
     * 通过qq的接口进行群签到, 默认使用“运势”的签到
     * @param http http送信器
     * @param info cookies信息与bkn信息
     * @param group 群号
     * @param poi   大概是地区位置的信息（字符串
     * @param text  大概是打卡的消息信息
     * @return 发送消息所得到的原始数据字符串。
     */
    public static String groupSign(HttpClientAble http, AuthInfo info, String group, String poi, String text) {
        String url = "http://qun.qq.com/cgi-bin/qiandao/sign/publish";

        Map<String, String> cookies = getAuthCookies(info);

        // 参数列表
        Map<String, String> ps = new HashMap<>(4);
        ps.put("bkn", info.getCsrfToken());
        ps.put("gc", group);
        ps.put("poi", poi);
        // 1 天气 2/3/4/5/6/7/9 默认的gallery_info无 运势 8
        ps.put("template_id=", "8");
        ps.put("gallery_info", "{\"category_id\":\"\",\"page\":\"\",\"pic_id\":\"\"}");
        ps.put("text", text);

        // 返回响应消息
        return http.postForm(url, toFormParam(ps), cookies);
    }

    /**
     * 获取到cookies的map
     * @param authInfo 权限信息
     */
    private static Map<String, String> getAuthCookies(AuthInfo authInfo){
        final String cks = authInfo.getCookies();
        return new HashMap<String, String>() {{
            final String[] split = cks.split("; *");
            for (String s : split) {
                final String[] kv = s.split("=");
                put(kv[0], kv[1]);
            }
        }};
    }

    /**
     * 将map参数拼接为form参数字符串
     * @param mapParam 参数集
     * @return 参数字符串
     */
    private static String toFormParam(Map<String, String> mapParam){
        final StringJoiner sb = new StringJoiner("&");
        mapParam.forEach((k, v) -> {
            sb.add(k + "=" + v);
        });
        return sb.toString();
    }






}
