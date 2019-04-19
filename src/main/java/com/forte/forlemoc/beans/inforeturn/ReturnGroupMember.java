package com.forte.forlemoc.beans.inforeturn;

import com.alibaba.fastjson.annotation.JSONField;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;

/**
 * 25303,以json串方式返回群成员信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 11:08
 * @since JDK1.8
 **/
public class ReturnGroupMember implements GroupMemberInfo {


    /** 返回编码，应该为 25303 */
    @JSONField(name = "return")
    private Integer returnCode;
    /*
    private String error,
    private String return,
    private String groupid,         // 群号
    private String QQID,            // QQ号
    private String username,         // 应该是QQ昵称
    private String nick,             // 应该是群名片
    private Integer sex,             // 性别 0/男 1/女
    private Integer age,             // 年龄
    private String area,             // 地区
    private String jointime,         // 入群时间
    private Long lastsent,           // 上次发言时间
    private String level_name,       // 头衔名字
    private Integer permission,      // 权限等级 1/成员 2/管理员 3/群主
    private Boolean unfriendly,      // 不良成员记录
    private String title,            // 自定义头衔
    private Long titleExpiretime,    // 头衔过期时间
    private Boolean nickcanchange    // 管理员是否能协助改名
     */
    private Integer error;
    private String groupid;
    private String QQID;
    private String username;
    private String nick;
    private Integer sex;
    private Integer age;
    private String area;
    private String jointime;
    private Long lastsent;
    private String level_name;
    private Integer permission;
    private Integer unfriendly;
    private String title;
    private Long titleExpiretime;
    /** 允许修改名片，1允许，猜测0是不允许；  */
    private Integer nickcanchange;

    public Integer getReturn() {
        return returnCode;
    }

    public void setReturn(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

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

    /* ———————— 统一接口 ———————— */

    /**
     * 获取群号
     */
    @Override
    public String getCode() {
        return groupid;
    }

    /**
     * 成员QQ号
     */
    @Override
    public String getQQ() {
        return QQID;
    }

    /**
     * qq昵称
     */
    @Override
    public String getName() {
        return username;
    }

    /**
     * 群昵称
     */
    @Override
    public String getNickName() {
        return nick;
    }

    /**
     * 群名片
     */
    @Override
    public String getCard() {
        return level_name;
    }

    public SexType getSex() {
        return sex == 0 ? SexType.MALE : sex == 1 ? SexType.FEMALE : SexType.UNKNOWN;
    }

    /**
     * 所在城市
     */
    @Override
    public String getCity() {
        return area;
    }

    /**
     * 加群时间
     */
    @Override
    public Long getJoinTime() {
        try{
            return Long.parseLong(jointime);
        }catch (Exception e){
            return -1L;
        }
    }

    /**
     * 最后一次发言时间
     */
    @Override
    public Long getLastTime() {
        return lastsent;
    }

    /**
     * 权限类型
     */
    @Override
    public PowerType getPowerType() {
        int p = permission;
        p = p == 1 ? -1 : p == 2 ? 0 : 1;
        return PowerType.of(p);
    }

    /**
     * 获取专属头衔
     */
    @Override
    public String getExTitle() {
        return title;
    }

    /**
     * 群成员等级名称
     */
    @Override
    public String getLevelName() {
        return level_name;
    }

    /**
     * 是否为不良用户
     * 应该是1为不良
     */
    @Override
    public Boolean isBlack() {
        return unfriendly == 1;
    }

    /**
     * 是否允许修改群昵称
     */
    @Override
    public Boolean isAllowChangeNick() {
        return nickcanchange == 1;
    }

    /**
     * 头衔的有效期
     */
    @Override
    public Long getExTitleTime() {
        return titleExpiretime;
    }

    /**
     * 头像地址
     */
    @Override
    public String getHeadImgUrl() {
        return null;
    }

    /**
     * 禁言剩余时间
     */
    @Override
    public Long getBanTime() {
        return -1L;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public Long getLastsent() {
        return lastsent;
    }

    public void setLastsent(Long lastsent) {
        this.lastsent = lastsent;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Integer getUnfriendly() {
        return unfriendly;
    }

    public void setUnfriendly(Integer unfriendly) {
        this.unfriendly = unfriendly;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTitleExpiretime() {
        return titleExpiretime;
    }

    public void setTitleExpiretime(Long titleExpiretime) {
        this.titleExpiretime = titleExpiretime;
    }

    public Integer getNickcanchange() {
        return nickcanchange;
    }

    public void setNickcanchange(Integer nickcanchange) {
        this.nickcanchange = nickcanchange;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String toString() {
        return "ReturnGroupMember{" +
                "returnCode=" + returnCode +
                ", error=" + error +
                ", groupid='" + groupid + '\'' +
                ", QQID='" + QQID + '\'' +
                ", username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", area='" + area + '\'' +
                ", jointime='" + jointime + '\'' +
                ", lastsent=" + lastsent +
                ", level_name='" + level_name + '\'' +
                ", permission=" + permission +
                ", unfriendly=" + unfriendly +
                ", title='" + title + '\'' +
                ", titleExpiretime=" + titleExpiretime +
                ", nickcanchange=" + nickcanchange +
                '}';
    }
}
