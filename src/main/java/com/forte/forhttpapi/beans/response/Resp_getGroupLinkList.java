package com.forte.forhttpapi.beans.response;

import com.forte.qqrobot.beans.messages.result.GroupLinkList;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getGroupLinkList implements GroupLinkList {
    private Integer status;
    private GroupLinkListName[] result;
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

    public void setResult(GroupLinkListName[] result) {
        this.result = result;
    }

    public GroupLinkListName[] getResult() {
        return result;
    }

    @Override
    public GroupLinkListName[] getList() {
        return result;
    }

    /*
    {
    "status":0,
    "result":[
        {
            "raw_url":"https://www.appnode.com/?0ja3cp",
            "seq":243,
            "thumbnail":"https://www.appnode.com/images/logo-s.gif",
            "time":1502607100,
            "title":"正在加载活动页面...",
            "uin":12345
        }
    ]
}
            result	array	链接信息列表
            result[i]	object	第i+1个链接信息
            result[i].raw_url	string	该链接发出来时的url
            result[i].thumbnail	string	该链接的站点图片
            result[i].time	int	该链接发布时间，时间戳形式
            result[i].title	string	该链接的简要内容
            result[i].uin	number	发布该链接的QQ

     */
    public static class GroupLinkListName implements GroupLink {
        private String raw_url;
        private String seq;
        private String thumbnail;
        private Long time;
        private String title;
        private String uin;

        public String getRaw_url() {
            return raw_url;
        }

        public void setRaw_url(String raw_url) {
            this.raw_url = raw_url;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public String getUrl() {
            return raw_url;
        }

        @Override
        public String getPicUrl() {
            return thumbnail;
        }

        @Override
        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getQQ() {
            return null;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUin() {
            return uin;
        }

        public void setUin(String uin) {
            this.uin = uin;
        }
    }

}
