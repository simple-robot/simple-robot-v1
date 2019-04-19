package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.PrivateMsgType;

/**
 * 私信接收到的信息封装
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/7 10:52
 * @since JDK1.8
 **/
public class MsgPrivate implements PrivateMsg {
    /*
     {
            "nick":     "法欧特桑",
            "sex":      "1",
            "age":      "12",
            "error":    "0",
            "act":      "21",
            "fromQQ":   "1149159218",
            "subType":  "11",
            "sendTime": "138",
            "font":     "1753080",
            "msg":      "hi"
    }
     */
            /** 消息类型：
             * 21：私聊信息，subType （子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组）
             * 2：群消息，   "subType": "1"
             * 4：讨论组消息，subType （子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组）
             * 事件类型：
             * 101：群事件-管理员变动，   subType（1/被取消管理员 2/被设置管理员）
             * 102：群事件-群成员减少，   subType（子类型，1/群员离开 2/群员被踢 3/自己(即登录号)被踢）
             * 103：群事件-群成员增加，   subType（子类型，1/管理员已同意 2/管理员邀请）
             * 201：好友事件-好友已添加， subType
             * 301：请求-好友添加，      subType
             * 302：请求-群添加         subType（子类型，1/他人申请入群 2/自己(即登录号)受邀入群）
             * */
            private Integer act;
             /** 昵称 */
             private String nick;
             /** 性别，0：男，1：女 */
             private Integer sex;
             /** 年龄 */
             private Integer age;
             /** 来自的QQ号 */
             private String fromQQ;
             /** 信息子类型 */
             private Integer subType;
             /** 发送时间 */
             private Long sendTime;
             /** 大概是字体 */
             private String font;
             /** 信息主体 */
             private String msg;
             /** 错误码 */
            private Integer error;

             /* ———————————— getter & setter ———————————— */


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取私聊消息类型
     * （子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组）
     */
    @Override
    public PrivateMsgType getType() {
        switch (subType) {
            case 11 : return PrivateMsgType.FROM_FRIEND;
            case 1 : return PrivateMsgType.FROM_ONLINE;
            case 2 : return PrivateMsgType.FROM_GROUP;
            case 3 : return PrivateMsgType.FROM_DISCUSS;
            default: return null;
        }
    }

    /**
     * 获取发送人的QQ号
     */
    @Override
    public String getQQ() {
        return fromQQ;
    }

    public String getFont() {
        return font;
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return sendTime;
    }

    public void setFont(String font) {
        this.font = font;
    }

    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
