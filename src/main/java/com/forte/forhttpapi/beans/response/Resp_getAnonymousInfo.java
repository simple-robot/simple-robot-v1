package com.forte.forhttpapi.beans.response;

import com.forte.qqrobot.beans.messages.result.AnonInfo;

/**
 * 「取匿名成员信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:37
 * @since JDK1.8
 **/
public class Resp_getAnonymousInfo implements AnonInfo {
    /*
    {
    "status":0,
    "result":{
        "aid":1000013,
        "code":"曹植",
        "token":"McFYb54/3LKw=="
    }
}
     */
    private Integer status;
    private AnonymousInfo result;
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

    public AnonymousInfo getResult() {
        return result;
    }

    public void setResult(AnonymousInfo result) {
        this.result = result;
    }

    @Override
    public String getId() {
        return result.aid+"";
    }

    @Override
    public String getAnonName() {
        return result.code;
    }

    @Override
    public String token() {
        return result.token;
    }

    /**
     * ——————————————内部类，封装信息
     */
    public static class AnonymousInfo  {
        /*
         aid	int	匿名成员ID
         code	string	匿名成员代号，如大力鬼王
         token	string	匿名成员Token，此参数值经过base64编码，原数据为数据流类型
         */
        private Integer aid;
        private String code;
        private String token;

        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}

