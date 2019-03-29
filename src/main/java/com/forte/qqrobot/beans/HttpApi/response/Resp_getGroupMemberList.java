package com.forte.qqrobot.beans.HttpApi.response;

import com.forte.qqrobot.beans.HttpApi.response.beaninter.RespGenderBean;
import com.forte.qqrobot.beans.HttpApi.response.beaninter.RespPowerBean;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getGroupMemberList implements RespBean<Resp_getGroupMemberList.GroupMemberList[]> {
    private Integer status;
    private GroupMemberList[] result;
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

    public void setResult(GroupMemberList[] result) {
        this.result = result;
    }

    @Override
    public GroupMemberList[] getResult() {
        return result;
    }

    /*
  {
    "status":0,
    "result":[
        {
            "group":12345,
            "qq":67890,
            "name":"le",
            "card":"",
            "gender":255,
            "city":"",
            "joinTime":1501576738,
            "lastTime":1501829899,
            "power":1,
            "tip":"",
            "level":"",
            "inBlackList":0,
            "allowChangeCard":0,
            "tipExpireTime":0,
            "headimg":"http://q.qlogo.cn/headimg_dl?bs=qq&dst_uin=0&spec=100"
        }
    ]
}

result	array	群成员信息
result[i]	object	第i+1个群成员信息
result[i].qq	number	QQ号
result[i].name	string	QQ昵称
result[i].card	string	群名片
result[i].gender	int	性别ID，0/男性，1/女性，255/未知
result[i].city	string	所在城市
result[i].joinTime	int	加群时间，时间戳形式
result[i].lastTime	int	最后发言时间，时间戳形式
result[i].power	int	管理权限，1/成员，2/管理，3/群主
result[i].tip	string	专属头衔
result[i].level	string	群成员等级所对应的等级名称
result[i].card	string	群名片
result[i].inBlackList	int	不良用户，0/不是，1/是
result[i].allowChangeCard	int	允许修改名片，0/不允许，1/允许
result[i].tipExpireTime	int	头衔有效期，时间戳形式，-1为永不到期
result[i].headimg	string	QQ头像
     */
    public static class GroupMemberList implements RespGenderBean, RespPowerBean {
        private String qq;
        private String name;
        private String card;
        private Integer gender;
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
