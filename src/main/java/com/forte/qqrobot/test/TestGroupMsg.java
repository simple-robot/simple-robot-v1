package com.forte.qqrobot.test;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 *
 * 提供一个测试用的TestGroupMsg
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TestGroupMsg implements GroupMsg {

    private String QQ = "00000000";
    private String group = "111111111";
    private GroupMsgType type = GroupMsgType.NORMAL_MSG;
    private String id = "999";
    private String msg = "测试消息！";
    private String font = "1";
    private Long time = Instant.now().getEpochSecond();
    private String originalData = "{ data:\"测试消息！\" }";

    @Override
    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public GroupMsgType getType() {
        return type;
    }

    public void setType(GroupMsgType type) {
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    @Override
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    @Override
    public String toString() {
        return "TestGroupMsg{" +
                "QQ='" + QQ + '\'' +
                ", group='" + group + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", font='" + font + '\'' +
                ", time=" + time +
                ", originalData='" + originalData + '\'' +
                '}';
    }
}
