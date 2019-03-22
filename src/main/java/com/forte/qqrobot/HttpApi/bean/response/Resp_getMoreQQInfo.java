package com.forte.qqrobot.HttpApi.bean.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getMoreQQInfo implements RespBean<Resp_getMoreQQInfo.MoreQQInfo[]> {
    private Integer status;
    private MoreQQInfo[] result;
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

    public void setResult(MoreQQInfo[] result) {
        this.result = result;
    }

    @Override
    public MoreQQInfo[] getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":[
        {
            "n":"pony",
            "u":10001,
            "headimg":"http://q2.qlogo.cn/g?b=qq&k=Vjic48anMfN6ovAxw4eN94w&s=100&t=1483281655",
            "level":37
        },
        {
            "n":"一块乐",
            "u":12345,
            "headimg":"http://q2.qlogo.cn/g?b=qq&k=ffxWIb7R5Rzpia88aM9SNXg&s=100&t=1483281655",
            "level":81
        }
    ]
}
result	array	QQ信息
result[i]	object	第i+1个QQ信息
result[i].n	string	QQ昵称
result[i].u	string	QQ号
result[i].headimg	string	头像链接
result[i].level	int	QQ等级
     */
    class MoreQQInfo {
        private String f;
        private String u;

        public String getF() {
            return f;
        }

        public void setF(String f) {
            this.f = f;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }
    }

}
