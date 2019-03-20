package com.forte.client.listener;

import com.alibaba.fastjson.JSON;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.HttpClientUtil;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/20 15:36
 * @since JDK1.8
 **/
public class DuiLianListener implements MsgGroupListener {

    /** 对对联请求地址 */
    private static final String url = "https://ai-backend.binwang.me/chat/couplet/";

    @Filter(value = "对对联 *.+", at = true, keywordMatchType = KeywordMatchType.TRIM_REGEX)
    @Override
    public boolean onMessage(MsgGroup msg, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender) {
        //移除所有的CQ码和对对联 *
        //剩下的就是对联上联
        String up = cqCodeUtil.removeCQCodeFromMsg(msg.getMsg()).replaceAll("对对联 *", "");
        up = up.trim().replaceAll("[\r\n]*", "");

        sender.sendGroupMsg(msg.getFromGroup(), "让我想想...");

        //获取下联
        try {
            String json = HttpClientUtil.sendHttpGet(url + up);
            String output = JSON.parseObject(json).getString("output");

            sender.sendGroupMsg(msg.getFromGroup(), "且听~\r\n上联："+ up +"\r\n下联："+ output);
        }catch (Exception e){
            sender.sendGroupMsg(msg.getFromGroup(), "唔....好难╥﹏╥...");
        }
        return true;
    }
}
