package com.forte.qqrobot.beans.HttpApi.request.get;

import com.forte.qqrobot.beans.HttpApi.request.ReqBean;
import com.forte.qqrobot.beans.HttpApi.response.Resp_getFileInfo;

/**
 * 「取文件信息」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 16:44
 * @since JDK1.8
 **/
public class Req_getFileInfo implements ReqGetBean<Resp_getFileInfo> {

    private final String fun = "getFileInfo";
    /**
     * 文件标识，即插件所提交的参数file
     */
    private String source;

    @Override
    public String getFun() {
        return fun;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Class<Resp_getFileInfo> getResponseType() {
        return Resp_getFileInfo.class;
    }
}
