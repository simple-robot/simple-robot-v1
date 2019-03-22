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
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 10:28
 * @since JDK1.8
 **/
public class MoeSearchListener implements MsgGroupListener {

    /**
     * 萌娘百科地址
     */
    private static final String MOE_ITEM_NET = "https://zh.moegirl.org";


    /**
     * 百度百科地址
     */
    private static final String BAIDU_ITEM_NET = "https://baike.baidu.com/item";

    /**
     * 萌娘百科
     */
    @Filter(value = ".+[?？]", at = true, keywordMatchType = KeywordMatchType.TRIM_REGEX)
    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
       String key = getKey(msg.getMsg(), cqCodeUtil);

       sender.sendGroupMsg(msg.getFromGroup(), "让我去用无敌的百科查一查" + key + "~");

        //结果
        //问问萌娘百科
        StringJoiner joiner = askMoeItem(key);
        //如果没有查到
        if (joiner == null) {
            //如果没有查到
            //查询百度百科
            joiner = askBaiduItem(key);
        }

        //如果还是null，说明百度百科和萌娘百科都没有
        if (joiner == null) {
            joiner = new StringJoiner("");
            joiner.add("啊啦，度娘和萌娘百科的站娘都说不知道呢..那我也无能为力了");
        }

        sender.sendGroupMsg(msg.getFromGroup(), joiner.toString());

        return true;
    }

    /**
     * 获取查询语句
     */
    private String getSearchPath(String key) {
        return MOE_ITEM_NET + "/" + key;
    }


    /**
     * 查询百度百科
     */
    private StringJoiner askBaiduItem(String key) {
        StringJoiner joiner = new StringJoiner("\r\n", "", "\r\n————————来自百度百科");
        String searchPath = BAIDU_ITEM_NET + "/" + key;

        String htmlStr = HttpClientUtil.sendHttpGet(searchPath);
        Document infoDoc = Jsoup.parse(htmlStr);

        Elements elementsByClass = infoDoc.getElementsByClass("lemma-summary");
        if (elementsByClass.size() <= 0) {
            //如果没有查到,看看是不是多义词页面
            Elements listDiv = infoDoc.getElementsByClass("list-dot list-dot-paddingleft");
            if (listDiv.size() <= 0) {
                //还是没有找到
                return null;

            } else {
                //记录多义词条目
                joiner.add(key + "可以是: ");
                listDiv.forEach(li -> joiner.add("> " + li.text() + " > " + BAIDU_ITEM_NET + li.getElementsByTag("a").get(0).attr("href")));
            }

        } else {
            //能查询到
            joiner.add(elementsByClass.text());

            //查询类型条目
            Element tagInfo = infoDoc.getElementsByClass("basic-info cmn-clearfix").get(0);
            Elements dls = tagInfo.getElementsByTag("dl");
            dls.forEach(dl -> {
                Elements children = dl.children();
                StringBuilder inner = new StringBuilder();
                children.forEach(dddt -> {
                    if (dddt.tag().equals(Tag.valueOf("dt"))) {
                        inner.append(dddt.text() + "\t: ");
                    } else {
                        inner.append(dddt.text());
                        joiner.add(inner.toString());
                        inner.delete(0, inner.length());
                    }
                });
            });
        }
        return joiner;
    }



    /**
     * 查询萌娘百科
     */
    private StringJoiner askMoeItem(String key){
        StringJoiner joiner;
        //问问萌娘百科
        String searchResult = HttpClientUtil.sendHttpGet(getSearchPath(key));

        //没有查到，直接返回null
        if(searchResult == null){
            return null;
        }

        //查到了
        Document htmlDoc = Jsoup.parse(searchResult);
        //尝试查询简介
        Element infoSpan = Optional.ofNullable(htmlDoc.getElementsByClass("mw-headline")).filter(es -> es.size() > 0).map(es -> es.get(0)).orElse(null);
        if(infoSpan == null){
            //如果没有简介，说明是个多条目索引
            //消歧义页，获取第二个 mw-parser-output class标签
            Element ele = htmlDoc.getElementsByClass("mw-parser-output").get(1);

            Elements children = ele.children();
            //标题
            Element element = children.get(0);
            joiner = new StringJoiner("\r\n", element.text()+"\r\n" , "\r\n————————来自萌娘百科");

            //获取列表
            Elements lis = ele.getElementsByTag("li");
            lis.forEach(li -> joiner.add("> " + li.text()));
        }else{
            joiner = new StringJoiner("\r\n", key + "是:\r\n", "\n\r————————来自萌娘百科");
            //获取标题下的介绍信息
            Element firstHeading = htmlDoc.getElementById("firstHeading");
            Element bodyContent = firstHeading.nextElementSibling();
            Element toc = bodyContent.getElementById("toc").previousElementSibling();

            if(toc != null){
                joiner.add(toc.text());
            }

            //获取第一条介绍信息
            Element parent = infoSpan.parent();
            //获取第一个p标签的前一个标签
            while(!parent.nextElementSibling().tag().equals(Tag.valueOf("p"))){
                parent = parent.nextElementSibling();
            }


            //标题文字后的信息的第一条
            Element next = parent;
            //遍历接下来的p标签
            while (next.equals(parent) || next.tag().equals(Tag.valueOf("p"))) {
                joiner.add(next.text() + (next.equals(parent) ? ": " : ""));
                next = next.nextElementSibling();
            }

        }


        return joiner;
    }


    /**
     * 从原始的信息中提取key信息
     * @param key
     * @return
     */
    private String getKey(String key, CQCodeUtil cqCodeUtil){
        String realKey = cqCodeUtil.removeCQCodeFromMsg(key).trim();

        realKey = realKey.substring(0, realKey.length()-1).trim();

        //判断结尾是什么
//        if(realKey.endsWith("？") || realKey.endsWith("?")){

            //如果是问号结尾，则说明

//        }else if(String regex2 = ".+[?？] *(\\( *(moe| *baidu) *\\)|（ *(moe|baidu) *）)$";){
//
//        }




        return realKey.replaceAll(" +", " ").replaceAll(" ", "_");
    }


}
