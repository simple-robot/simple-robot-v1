package com.forte.forhttpapi.beans.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getRunStatus implements RespBean<Resp_getRunStatus.RunStatus> {
    private Integer status;
    private RunStatus result;
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

    public void setResult(RunStatus result) {
        this.result = result;
    }

    @Override
    public RunStatus getResult() {
        return result;
    }

    /*
{
    "status":0
}
     */
    public static class RunStatus {
        //该函数无其余返回值，仅在插件未出现无响应状态时正常返回数据
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
