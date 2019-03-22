package com.forte.qqrobot.HttpApi.bean.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getStrangerInfo implements RespBean<Resp_getStrangerInfo.StrangerInfo> {
    private Integer status;
    private StrangerInfo result;
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

    public void setResult(StrangerInfo result) {
        this.result = result;
    }

    @Override
    public StrangerInfo getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":{
        "qq":10001,
        "gender":0,
        "old":0,
        "name":"pony",
        "headimg":"http://q2.qlogo.cn/g?b=qq&k=Vjic48anMfN6ovAxw4eN94w&s=100&t=1483281655",
        "level":37
    }
}
result	object	QQ信息
result.qq	number	QQ号
result.gender	int	性别，0/男，1/女，255/未知
result.old	int	年龄
result.name	string	昵称
result.headimg	string	头像链接
result.level	int	QQ等级
     */
    class StrangerInfo {
        private String qq;
        private Integer gender;
        private Integer old;
        private String name;
        private String headimg;
        private Integer level;

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getOld() {
            return old;
        }

        public void setOld(Integer old) {
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
