package com.forte.qqrobot.sender;

import com.forte.qqrobot.beans.messages.result.AuthInfo;
import com.forte.qqrobot.sender.intercept.InterceptValue;

import java.net.URISyntaxException;
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

//    /**
//     * 发布群公告
//     * 目前，top、toNewMember、confirm参数是无效的
//     * @param group 群号
//     * @param title 标题
//     * @param text   正文
//     * @param top    是否置顶，默认false
//     * @param toNewMember 是否发给新成员 默认false
//     * @param confirm 是否需要确认 默认false
//     * @return 是否发布成功
//     */
//    public void sendGroupNotice(String group, String title, String text, boolean top, boolean toNewMember, boolean confirm){
//        throw new RuntimeException();
//    }

    /**
     * 通过qq的接口进行群签到
     * @param http http送信器
     * @param info cookies信息与bkn信息
     * @param group 群号
     * @param poi   大概是地区位置的信息（字符串
     * @param text  大概是打卡的消息信息
     */
    public static void groupSign(HttpClientAble http, AuthInfo info, String group, String poi, String text) {
        String url = "http://qun.qq.com/cgi-bin/qiandao/sign/publish";

        // uin=o2240189254; skey=MkS1etLBEu
        final String cks = info.getCookies();
        Map<String, String> cookies = new HashMap<String, String>() {{
            final String[] split = cks.split("; *");
            for (String s : split) {
                final String[] kv = s.split("=");
                put(kv[0], kv[1]);
            }
        }};

        Map<String, String> ps = new HashMap<>();


        ps.put("bkn", info.getCsrfToken());
        ps.put("gc", group);
        ps.put("poi", poi);
        ps.put("pic_id", "");
        ps.put("text", text);

        final StringJoiner sb = new StringJoiner("&");
        ps.forEach((k, v) -> {
            sb.add(k + "=" + v);
        });


        final String post = http.postForm(url, sb.toString(), cookies);

        System.out.println(url);
        System.out.println("send sign ? ");
        System.out.println("post: " + post);
    }
}
