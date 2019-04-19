package com.forte.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.DiscussMsg;

/**
 * 获取到的讨论组消息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:46
 * @since JDK1.8
 **/
public class MsgDisGroup implements DiscussMsg {
    /*
    4 讨论组消息
    subType，sendTime，fromDiscuss，fromQQ，msg，font, nick, sex, age
     */
    /** 消息类型 */
    private Integer act;
    /** 昵称 */
    private String nick;
    /** 性别 */
    private Integer sex;
    /** 年龄 */
    private Integer age;
    /** 子类型 */
    private Integer subType;
    /** 送信事件 */
    private Long sendTime;
    /** 来自的讨论组 */
    private String fromDiscuss;
    /** 发送消息的人 */
    private String fromQQ;
    /** 消息正文 */
    private String msg;
    /** 字体 */
    private String font;
    /** 错误码 */
    private Integer error;

    /* ———————— getter & setter ———————— */

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

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

    public String getFromDiscuss() {
        return fromDiscuss;
    }

    public void setFromDiscuss(String fromDiscuss) {
        this.fromDiscuss = fromDiscuss;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }



    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return sendTime;
    }

    /**
     * 获取讨论组号
     */
    @Override
    public String getGroup() {
        return fromDiscuss;
    }

    /**
     * 获取发消息的人的QQ
     */
    @Override
    public String getQQ() {
        return fromQQ;
    }

    /**
     * 如果还会出现其他的参数，请自行实现获取方式<br>
     * 如果其他参数不多，你可以试着在方法中使用if_else进行分别；<br>
     * 如果参数较多，我建议你使用Map等键值对来储存参数并获取
     *
     * @param key
     */
    @Override
    public Object getOtherParam(String key) {
        switch (key) {
            case "nick" : return nick;
            case "sex" : return sex;
            case "age" : return age;
            case "error" : return error;
            default: return null;
        }
    }
}
