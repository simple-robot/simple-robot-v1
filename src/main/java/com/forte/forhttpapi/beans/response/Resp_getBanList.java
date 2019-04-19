package com.forte.forhttpapi.beans.response;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:46
 * @since JDK1.8
 **/
public class Resp_getBanList implements RespBean<Resp_getBanList.BanList[]> {

    private Integer status;
    private BanList[] result;
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

    @Override
    public BanList[] getResult() {
        return result;
    }

    public void setResult(BanList[] result) {
        this.result = result;
    }

    /**
     * ——————————内部类
     */
    public static class BanList{
        /*
            manager	int	该成员是否为管理，此字段固定为1；当成员非管理时，此字段不存在
            nick	string	该成员的群名片
            t	int	该成员离解禁的剩余时间，单位：秒
            uin	number	该成员的QQ号
         */
        private Integer manager;
        private String nick;
        private Integer t;
        private String uin;

        public Integer getManager() {
            return manager;
        }

        public void setManager(Integer manager) {
            this.manager = manager;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Integer getT() {
            return t;
        }

        public void setT(Integer t) {
            this.t = t;
        }

        public String getUin() {
            return uin;
        }

        public void setUin(String uin) {
            this.uin = uin;
        }
    }

}
