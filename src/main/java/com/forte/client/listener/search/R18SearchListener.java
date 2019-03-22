package com.forte.client.listener.search;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/20 16:52
 * @since JDK1.8
 **/
public class R18SearchListener implements MsgGroupListener {

    private static final String net = "http://www.btdiggzw.net";

    private static final String KEY_NAME = "资源";

    @Filter(value = KEY_NAME + "(\\(\\d+\\)(,\\d)?)? *.+", at = true, keywordMatchType = KeywordMatchType.TRIM_REGEX)
    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
    try{
        String m = cqCodeUtil.removeCQCodeFromMsg(msg.getMsg()).trim();

        String searchUrl;
        int page = 1;
        //截取段，默认-1
        int limit = -1;
        int i = m.indexOf(KEY_NAME + "(");
        if(i != 0){
            //如果获取不是在第一个，则说明没有页码，直接移除前两个然后去空格
            m = m.substring(2).trim();
            searchUrl = getSearchUrl(m);
        }else{
            //如果是在第一个，说明有页码，截取出页码
            int start = m.indexOf("(");
            int end = m.indexOf(")");
            String pageStr = m.substring(start + 1, end);
            //有可能有分段限制
            String[] split = pageStr.split("\\,");
            //如果长度不为1，则说明有分段限制
            if(split.length > 1){
                limit = Integer.parseInt(split[1]) ;
            }

            //获取页码
            page = Integer.parseInt(split[0]) < 0 ? 1 : Integer.parseInt(split[0]);
            m = m.substring(end+1).trim();
            searchUrl = getSearchUrl(m, page);

        }

        //输出提示语句
        String limitStr = limit > 0 ? "前"+ limit +"条" : "";
        sender.sendGroupMsg(msg.getFromGroup(), "搜索第"+ page +"页"+ limitStr +"的 "+ m +" 是吧，我懂了~");

        //获取查询结果
        String s = HttpClientUtil.sendHttpGet(searchUrl);
        Document parse = Jsoup.parse(s);
        Element attr = parse.body().attr("class", "i_list");
        Elements li = attr.getElementsByTag("li");

        AtomicInteger index = new AtomicInteger(0);
        Stream<Element> liStream = li.stream();

        //如果有条数限制，进行限制
        if(limit > 0){
            liStream = liStream.limit(limit);
        }

        liStream.forEach(e -> {
            String sea = index.addAndGet(1) + " - \r\n";
            String href = net + e.getElementsByTag("a").attr("href");
            //有了地址了
            String info = HttpClientUtil.sendHttpGet(href);
            Document infoDoc = Jsoup.parse(info);
            Element div = infoDoc.attr("class", "i_left").getElementById("mirror-usage");
            String title = div.getElementsByTag("h3").text();
            String path = div.getElementsByTag("pre").text();
            sea += "标题：" + title + "\r\n" +
                   "磁链：" + path + "\r\n";
            sea += "信息：" + e.text();
            //返回结果
            sender.sendGroupMsg(msg.getFromGroup(), sea);
        });

        //如果没有数据，则展示没有数据
        if(index.get() == 0){
            sender.sendGroupMsg(msg.getFromGroup(), "唔...好像什么也搜不到呢..");
        }

        return true;
    }catch (Exception e){
        sender.sendGroupMsg(msg.getFromGroup(), "唔...好像出现了一些错误..");
        return true;
    }
    }


    /**
     * 获取查询列表
     * @return
     */
    private String getSearchUrl(String key){
        return net + "/s/" + key;
    }


    /**
     * 获取查询列表
     * @return
     */
    private String getSearchUrl(String key, int page){
        return net + "/s/" + key + "/" + page + ".htm";
    }

}
