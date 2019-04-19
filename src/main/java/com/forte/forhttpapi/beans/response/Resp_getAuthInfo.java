package com.forte.forhttpapi.beans.response;

import com.forte.qqrobot.beans.messages.result.AuthInfo;

/**
 * 「取权限信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:41
 * @since JDK1.8
 **/
public class Resp_getAuthInfo implements AuthInfo {

    private Integer status;
    private AuthInfo result;
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

    public AuthInfo getResult() {
        return result;
    }

    public void setResult(AuthInfo result) {
        this.result = result;
    }

    @Override
    public String getCode() {
        return result.authCode;
    }

    @Override
    public String getCookies() {
        return result.cookies;
    }

    @Override
    public String getCsrfToken() {
        return result.csrfToken+"";
    }

    /**
     * ——————————内部类
     */
    public static class AuthInfo{
        /*
       authCode	    int	AuthCode，似乎没什么用
       cookies	    string	Cookies
       csrfToken	number	CsrfToken，即QQ网页用到的 bkn/g_tk等
         */
        private String authCode;
        private String cookies;
        private Integer csrfToken;


        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getCookies() {
            return cookies;
        }

        public void setCookies(String cookies) {
            this.cookies = cookies;
        }

        public Integer getCsrfToken() {
            return csrfToken;
        }

        public void setCsrfToken(Integer csrfToken) {
            this.csrfToken = csrfToken;
        }
    }
}
