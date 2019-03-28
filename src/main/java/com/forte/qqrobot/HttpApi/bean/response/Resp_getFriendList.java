package com.forte.qqrobot.HttpApi.bean.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getFriendList implements RespBean<Resp_getFriendList.FriendList[]> {
    private Integer status;
    private FriendList[] result;
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

    public void setResult(FriendList[] result) {
        this.result = result;
    }

    @Override
    public FriendList[] getResult() {
        return result;
    }

    /*
      {
    "status":0,
    "result":{
        "1":{
            "gname":"disKnow",
            "mems":[
                {
                    "name":"BBQ",
                    "uin":99999
                }
            ]
        },
        "2":{
            "gname":"ther",
            "mems":[
                {
                    "name":"友人A",
                    "uin":12345
                }
            ]
        }
    }
}
         result	array	好友列表数组
         result[i]	array	第i+1个分组信息
         result[i].gname	string	该分组的组名
         result[i].mems	array	该分组的用户列表信息
         result[i].mems[n]	array	该分组的第n+1个用户信息
         result[i].mems[n].name	string	该用户的备注
         result[i].mems[n].uin	number	该用户的QQ号
                  */
    public static class FriendList {
        private String gname;
        //TODO 这里是一个分组下的所有好友
        private List<Map<String, String>> mems = new ArrayList<>();

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public List<Map<String, String>> getMems() {
            return mems;
        }

        public void setMems(List<Map<String, String>> mems) {
            this.mems = mems;
        }
    }

}
