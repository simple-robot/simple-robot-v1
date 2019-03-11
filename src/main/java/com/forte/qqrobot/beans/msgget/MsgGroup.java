package com.forte.qqrobot.beans.msgget;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/7 11:09
 * @since JDK1.8
 **/
public class MsgGroup implements MsgGet {
    /*
    {
         "username":        "泰兰德",
         "nick":            "泰奶奶",
         "sex":             "0",
         "age":             "12000",
         "error":           "0",
         "act":             "2",
         "fromGroup":       "1234",
         "fromGroupName":   "月之女祭司",
         "fromQQ":          "1234",
         "subType":         "1",
         "sendTime":        "1481481775",
         "fromAnonymous":   "",
         "msg":             "谁看到玛法里奥了？",
         "font":            "7141560"
     }
     */
            /** 用户名 */
            private String username;
            /** 群昵称 */
            private String nick;
            /** 性别，0：男，1：女 */
            private String sex;
            /** 年龄 */
            private String age;
            /** 消息类型，群消息一般为2 */
            private Integer act;
            /** 群号 */
            private String fromGroup;
            /** 群昵称 */
            private String fromGroupName;
            /** qq号，发消息的人 */
            private String fromQQ;
            /** 子类型 */
            private Integer subType;
            /** 发送时间 */
            private Long sendTime;
            /** 如果是匿名应该会有匿名名称 */
            private String fromAnonymous;
            /** 消息正文 */
            private String msg;
            /** 字体 */
            private String font;
            /** 错误编码，无错为0 */
            private String error;

            /* —————————— getter & setter —————————— */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getFromGroupName() {
        return fromGroupName;
    }

    public void setFromGroupName(String fromGroupName) {
        this.fromGroupName = fromGroupName;
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

    public String getFromAnonymous() {
        return fromAnonymous;
    }

    public void setFromAnonymous(String fromAnonymous) {
        this.fromAnonymous = fromAnonymous;
    }

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
}
