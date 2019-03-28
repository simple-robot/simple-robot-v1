package com.forte.qqrobot.HttpApi.bean.response;

import com.forte.qqrobot.HttpApi.bean.response.beaninter.RespGenderBean;
import com.forte.qqrobot.HttpApi.bean.response.beaninter.RespPowerBean;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getGroupMemberInfo implements RespBean<Resp_getGroupMemberInfo.GroupMemberInfo> {
    private Integer status;
    private GroupMemberInfo result;
    private String errMsg;

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(GroupMemberInfo result) {
        this.result = result;
    }

    @Override
    public GroupMemberInfo getResult() {
        return result;
    }

    /*
    {
    "status":0,
    "result":{
        "group":12345,
        "qq":67890,
        "name":"name",
        "card":"card",
        "gender":0,
        "old":18,
        "city":"",
        "joinTime":1466098191,
        "lastTime":1503840192,
        "power":3,
        "tip":"自定义头衔",
        "level":"传说",
        "inBlackList":0,
        "allowChangeCard":1,
        "tipExpireTime":-1,
        "headimg":"http://q.qlogo.cn/headimg_dl?bs=qq&dst_uin=67890&spec=100"
    }
}

result	object	群成员信息
result.group	number	群号
result.qq	number	QQ号
result.name	string	QQ昵称
result.card	string	群名片
result.gender	int	性别ID，0/男性，1/女性，255/未知
result.city	string	所在城市
result.joinTime	int	加群时间，时间戳形式
result.lastTime	int	最后发言时间，时间戳形式
result.power	int	管理权限，1/成员，2/管理，3/群主
result.tip	string	专属头衔
result.level	string	群成员等级所对应的等级名称
result.card	string	群名片
result.inBlackList	int	不良用户，0/不是，1/是
result.allowChangeCard	int	允许修改名片，0/不允许，1/允许
result.tipExpireTime	int	头衔有效期，时间戳形式，-1为永不到期
result.headimg	string	QQ头像
     */
    public static class GroupMemberInfo implements RespGenderBean, RespPowerBean {
        private String group;
        private String qq;
        private String name;
        private String card;
        private Integer gender;
        private String old;
        private String city;
        private String joinTime;
        private String lastTime;
        private Integer power;
        private String tip;
        private String level;
        private Integer inBlackList;
        private Integer allowChangeCard;
        private Integer tipExpireTime;
        private String headimg;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getOld() {
            return old;
        }

        public void setOld(String old) {
            this.old = old;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Integer getInBlackList() {
            return inBlackList;
        }

        public void setInBlackList(Integer inBlackList) {
            this.inBlackList = inBlackList;
        }

        public Integer getAllowChangeCard() {
            return allowChangeCard;
        }

        public void setAllowChangeCard(Integer allowChangeCard) {
            this.allowChangeCard = allowChangeCard;
        }

        public Integer getTipExpireTime() {
            return tipExpireTime;
        }

        public void setTipExpireTime(Integer tipExpireTime) {
            this.tipExpireTime = tipExpireTime;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }

}
