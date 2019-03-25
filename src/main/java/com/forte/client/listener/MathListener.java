package com.forte.client.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.listener.MsgGroupListener;
import com.forte.qqrobot.listener.MsgPrivateListener;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 计算监听器
 * 群聊需要被at
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 16:30
 * @since JDK1.8
 **/
public class MathListener implements MsgPrivateListener, MsgGroupListener {


    /**
     * 计算，群组消息需要被at
     */
    @Override
    @Filter(at = true)
    public boolean onMessage(MsgGroup msgGroup, CQCode[] cqCodes, boolean b, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender qqWebSocketMsgSender) {
        String end = run(cqCodeUtil.removeCQCodeFromMsg(msgGroup.getMsg()));
        if(end != null){
            qqWebSocketMsgSender.sendGroupMsg(msgGroup.getFromGroup(), end);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 计算
     */
    @Override
    public boolean onMessage(MsgPrivate msgPrivate, CQCode[] cqCodes, boolean b, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender qqWebSocketMsgSender) {
        String end = run(msgPrivate.getMsg());
        if(end != null){
            qqWebSocketMsgSender.sendMsgPrivate(msgPrivate.getFromQQ(), end);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 尝试使用js执行字符串，如果不能执行则返回false
     * @return 是否可以执行
     */
    public String run(String msg){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        try{
            return engine.eval(msg).toString();
        }catch(Exception e){
            return null;
        }
    }

}
