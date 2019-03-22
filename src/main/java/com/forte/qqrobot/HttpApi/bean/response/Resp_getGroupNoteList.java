package com.forte.qqrobot.HttpApi.bean.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getGroupNoteList implements RespBean<Resp_getGroupNoteList.GroupNoteList[]> {
    private Integer status;
    private GroupNoteList[] result;

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(GroupNoteList[] result) {
        this.result = result;
    }

    @Override
    public GroupNoteList[] getResult() {
        return result;
    }

    /*
 {
    "status":0,
    "result":[
        {
            "cn":0,
            "fid":"890aa71260400",
            "fn":0,
            "msg":{
                "pics":[
                    {
                        "id":"XfzBqF5ggFwRKRSQ05ctWJY7Hc"
                    }
                ],
                "text":"xxx",
                "text_face":"xxxa",
                "title":"领取授权通知"
            },
            "pubt":1492941154,
            "read_num":1,
            "settings":{
                "is_show_edit_card":0
            },
            "type":6,
            "u":67890,
            "vn":0
        }
    ]
}
result	array	公告信息数组
result[i]	object	第i+1个公告信息
result[i].fid	string	公告ID
result[i].msg	object	公告信息数组
result[i].msg.text	string	公告内容(完整)
result[i].msg.text_face	string	公告内容(预览)
result[i].msg.title	string	公告标题
result[i].pubt	int	发布时间戳
result[i].read_num	int	已阅读的人数
result[i].settings	object	公告附带的信息
result[i].settings.is_show_edit_card	int	提醒群成员修改名片，1/提醒，0/不提醒
result[i].type	int	公告类型ID
result[i].u	number	发布人QQ
     */
    class GroupNoteList {
        private String cn;
        private String fid;
        private String fn;
        // TODO 公告信息数组
        private List<Map<String, Object>> msg = new ArrayList<>();
        private String pubt;
        private Integer read_num;
        // TODO 公告附带的信息
        private List<Map<String, Object>> settings = new ArrayList<>();
        private Integer type;
        private String u;
        private String vn;

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getFn() {
            return fn;
        }

        public void setFn(String fn) {
            this.fn = fn;
        }

        public List<Map<String, Object>> getMsg() {
            return msg;
        }

        public void setMsg(List<Map<String, Object>> msg) {
            this.msg = msg;
        }

        public String getPubt() {
            return pubt;
        }

        public void setPubt(String pubt) {
            this.pubt = pubt;
        }

        public Integer getRead_num() {
            return read_num;
        }

        public void setRead_num(Integer read_num) {
            this.read_num = read_num;
        }

        public List<Map<String, Object>> getSettings() {
            return settings;
        }

        public void setSettings(List<Map<String, Object>> settings) {
            this.settings = settings;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getVn() {
            return vn;
        }

        public void setVn(String vn) {
            this.vn = vn;
        }
    }

}
