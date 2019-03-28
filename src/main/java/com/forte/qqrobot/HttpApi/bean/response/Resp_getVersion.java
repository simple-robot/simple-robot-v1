package com.forte.qqrobot.HttpApi.bean.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getVersion implements RespBean<Resp_getVersion.Version> {
    private Integer status;
    private Version result;
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

    public void setResult(Version result) {
        this.result = result;
    }

    @Override
    public Version getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":{
        "cq":"air",
        "plugin":"2.1.2(204)"
    }
}
result	object	QQ信息
result.cq	string	酷Q版本，air/pro
result.plugin	string	插件版本
     */
    public static class Version {
        private String cq;
        private String plugin;

        public String getCq() {
            return cq;
        }

        public void setCq(String cq) {
            this.cq = cq;
        }

        public String getPlugin() {
            return plugin;
        }

        public void setPlugin(String plugin) {
            this.plugin = plugin;
        }
    }

}
