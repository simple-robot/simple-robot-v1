package com.forte.qqrobot.beans.HttpApi.response;

/**
 * 「取权限信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 15:41
 * @since JDK1.8
 **/
public class Resp_getAuthInfo implements RespBean<Resp_getAuthInfo.AuthInfo> {

    private Integer status;
    private AuthInfo result;
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
    public AuthInfo getResult() {
        return result;
    }

    public void setResult(AuthInfo result) {
        this.result = result;
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
        private Integer authCode;
        private String cookies;
        private Integer csrfToken;


        public Integer getAuthCode() {
            return authCode;
        }

        public void setAuthCode(Integer authCode) {
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
