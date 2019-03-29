package com.forte.qqrobot.beans.HttpApi.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getMoreGroupHeadimg implements RespBean<Resp_getMoreGroupHeadimg.MoreGroupHeadimg[]> {
    private Integer status;
    private MoreGroupHeadimg[] result;
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

    public void setResult(MoreGroupHeadimg[] result) {
        this.result = result;
    }

    @Override
    public MoreGroupHeadimg[] getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":[
        {
            "f":"http://thirdqq.qlogo.cn/g?b=sdk&k=ibx1xDias1jg&s=40&t=0",
            "u":140
        },
        {
            "f":"http://thirdqq.qlogo.cn/g?b=sdk&k=VeicEFmPw&s=40&t=45",
            "u":463
        }
    ]
}
result	array	头像信息
result[i]	object	第i+1个头像信息
result[i].f	string	头像链接
result[i].u	string	头像对应的群号
     */
    public static class MoreGroupHeadimg {
        private String n;
        private String u;
        private String headimg;
        private Integer level;

        public String getN() {

            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }
    }

}
