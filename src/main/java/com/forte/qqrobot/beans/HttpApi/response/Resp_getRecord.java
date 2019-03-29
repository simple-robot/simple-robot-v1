package com.forte.qqrobot.beans.HttpApi.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getRecord implements RespBean<Resp_getRecord.getRecord> {
    private Integer status;
    private getRecord result;
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

    public void setResult(getRecord result) {
        this.result = result;
    }

    @Override
    public getRecord getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":{
        "name":"F87451BBBBB973465AAB45CB66A1352D.mp3",
        "file":"SUQzBAADTGF2ZjU2LjM2LjEqq"
    }
}
result	object	文件信息
result.name	string	转码后保存在语音目录下的文件名
result.file	string	已Base64编码的文件内容
     */
    public static class getRecord {
        private String name;
        private String file;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }

}
