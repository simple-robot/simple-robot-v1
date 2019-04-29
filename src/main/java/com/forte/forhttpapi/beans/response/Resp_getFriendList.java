package com.forte.forhttpapi.beans.response;

import com.forte.qqrobot.beans.messages.result.FriendList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getFriendList implements FriendList {
    private Integer status;
    private FriendList[] result;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(FriendList[] result) {
        this.result = result;
    }

    public FriendList[] getResult() {
        return result;
    }

    @Override
    public Map<String, Friend[]> getFriendList() {
        return null;
    }

    @Override
    public FriendList[] getFirendList(String group) {
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
    public static class FriendList implements Friend {
        private String name;
        private String uin;

        public void setName(String name) {
            this.name = name;
        }

        public String getUin() {
            return uin;
        }

        public void setUin(String uin) {
            this.uin = uin;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getQQ() {
            return uin;
        }
    }

}
