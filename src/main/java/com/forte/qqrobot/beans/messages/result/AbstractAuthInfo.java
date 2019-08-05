package com.forte.qqrobot.beans.messages.result;

/**
 * @see AuthInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractAuthInfo extends AbstractInfoResult implements AuthInfo {

    private String code;

    private String cookies;

    private String csrfToken;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "code='" + getCode() + '\'' +
                ", cookies='" + getCookies() + '\'' +
                ", csrfToken='" + getCsrfToken() + '\'' +
                "} " + super.toString();
    }
}
