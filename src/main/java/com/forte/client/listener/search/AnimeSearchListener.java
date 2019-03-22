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

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/21 18:32
 * @since JDK1.8
 **/
public class AnimeSearchListener implements MsgGroupListener {

    private static final String net = "https://www.dongmanhuayuan.com";

    private static final String KEY_NAME = "番";

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
        sender.sendGroupMsg(msg.getFromGroup(), "找一找第"+ page +"页"+ limitStr +"的 "+ m +" 是吧，明白啦~");

        //获取查询结果
        try{

            String s = HttpClientUtil.sendHttpGet(searchUrl);
            Document parse = Jsoup.parse(s);
            Element attr = parse.body().attr("class", "list-item");
            Elements dd = attr.getElementsByTag("dd");
            AtomicInteger index = new AtomicInteger(0);

            Stream<Element> liStream = dd.stream();

            //如果有条数限制，进行限制
            if(limit > 0){
                liStream = liStream.limit(limit);
            }


            liStream.forEach(e -> {
                        //获取span标签
                        Elements spans = e.getElementsByTag("span");
                        StringJoiner joiner = new StringJoiner("\r\n", index.addAndGet(1) + "-\r\n", "");
                        spans.forEach(sp -> {
                            String aClass = sp.attr("class");
                            if("a".equals(aClass)){
                                //按照顺序，分别是更新时间、资源地址、空行、大小和下载地址
                                joiner.add("上传时间: "+sp.text());
                            }else if("b".equals(aClass)){
                                //是b,则是地址

                                Element pathA = sp.getElementsByTag("a").get(0);

                                String title = pathA.text();
                                joiner.add("标题: " + title);

                                String info = HttpClientUtil.sendHttpGet(net + pathA.attr("href"));
                                Document infoDoc = Jsoup.parse(info);
                                Element elList = infoDoc.attr("class", "desc-box");

                                Element find = elList.getElementsByClass("desc-list-item").stream().filter(el -> el.text().contains("磁力地址")).findFirst().orElse(null);

                                Elements p = find.getElementsByTag("p");
                                StringJoiner pathJoiner = new StringJoiner("\r\n");
                                p.forEach(ep -> pathJoiner.add(ep.text()));
                                joiner.add("磁链: ").add(pathJoiner.toString());
                            }else if("d".equals(aClass)){
                                joiner.add("大小: " + sp.text().replaceAll("下载文件", ""));
                            }
                        });

                        //结果查询完毕
                        sender.sendGroupMsg(msg.getFromGroup(), joiner.toString());

            });

            //如果没有数据，则展示没有数据
            if(index.get() == 0){
                sender.sendGroupMsg(msg.getFromGroup(), "咦?什么也没查到呢!");
            }
        }catch (Exception e){
            sender.sendGroupMsg(msg.getFromGroup(), "咦?与网站的通讯好像出问题了,番剧搜索没有响应耶");
        }

        return true;
    }catch (Exception e){
        sender.sendGroupMsg(msg.getFromGroup(), "咦?好像...出现了一些错误呢");
        return true;
    }
    }

    /**
     * 获取查询列表
     * @return
     */
    private String getSearchUrl(String key){
        return net + "/search/" + key;
    }


    /**
     * 获取查询列表
     * @return
     */
    private String getSearchUrl(String key, int page){
        return net + "/search/" + key + "/" + page + ".html";
    }

}
