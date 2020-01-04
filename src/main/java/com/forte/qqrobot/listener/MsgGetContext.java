package com.forte.qqrobot.listener;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.intercept.BaseContext;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.util.Map;

/**
 *
 * 消息拦截器上下文对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgGetContext extends BaseContext<MsgGet> {

    /*
     * MsgGet对象。假如对此对象进行修改，会同样影响后续的监听
     * MsgGet对象保存在父类中。
     */

    /** 消息类型 */
    private MsgGetTypes types;
    /** sender */
    public final SenderSendList SENDER;
    /** setter */
    public final SenderSetList  SETTER;
    /** getter */
    public final SenderGetList  GETTER;

    //**************** value ****************//

    public MsgGet getMsgGet() {
        return getValue();
    }

    public void setMsgGet(MsgGet msgGet) {
       setValue(msgGet);
    }

    //**************** element ****************//


    public MsgGetTypes getTypes() {
        return types;
    }

    public void setTypes(MsgGetTypes types) {
        this.types = types;
    }

    public MsgGetContext(MsgGet msgGet, SenderSendList sender, SenderSetList setter, SenderGetList getter, Map<String, Object> globalContextMap){
        super(msgGet, globalContextMap);
        this.types = MsgGetTypes.getByType(msgGet);
        this.SENDER = sender;
        this.SETTER = setter;
        this.GETTER = getter;
    }
}
