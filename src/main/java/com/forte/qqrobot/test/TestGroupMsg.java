package com.forte.qqrobot.test;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;
import com.forte.qqrobot.beans.messages.types.PowerType;

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
@Deprecated
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

    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     *
     * @return 接收到此消息的账号。
     */
    @Override
    public String getThisCode() {
        return null;
    }

    /**
     * 允许重新定义Code以实现在存在多个机器人的时候切换处理。
     *
     * @param code code
     */
    @Override
    public void setThisCode(String code) {

    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
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

    /**
     * 获取此人在群里的权限
     *
     * @return 权限，例如群员、管理员等
     */
    @Override
    public PowerType getPowerType() {
        return null;
    }

    /**
     * 重新定义此人的权限
     *
     * @param powerType 权限
     */
    @Override
    public void setPowerType(PowerType powerType) {

    }
}
