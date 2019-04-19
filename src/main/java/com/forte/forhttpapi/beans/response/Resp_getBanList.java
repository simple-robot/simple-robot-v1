package com.forte.forhttpapi.beans.response;

import com.forte.qqrobot.beans.messages.result.BanList;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:46
 * @since JDK1.8
 **/
public class Resp_getBanList implements BanList {

    private Integer status;
    private BanList[] result;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(BanList[] result) {
        this.result = result;
    }

    public BanList[] getResult() {
        return result;
    }

    @Override
    public BanList[] getList() {
        return result;
    }

    /**
     * ——————————内部类
     */
    public static class BanList implements BanInfo {
        /*
            manager	int	该成员是否为管理，此字段固定为1；当成员非管理时，此字段不存在
            nick	string	该成员的群名片
            t	int	该成员离解禁的剩余时间，单位：秒
            uin	number	该成员的QQ号
         */
        private Boolean manager;
        private String nick;
        private Long t;
        private String uin;

        public Boolean getManager() {
            return manager;
        }

        public void setManager(Boolean manager) {
            this.manager = manager;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Long getT() {
            return t;
        }

        public void setT(Long t) {
            this.t = t;
        }

        public String getUin() {
            return uin;
        }

        public void setUin(String uin) {
            this.uin = uin;
        }

        @Override
        public String getQQ() {
            return uin;
        }

        @Override
        public String getNickName() {
            return nick;
        }

        @Override
        public Boolean isManager() {
            return manager;
        }

        @Override
        public Long lastTime() {
            return t;
        }
    }

}
