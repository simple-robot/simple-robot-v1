package com.forte.qqrobot.HttpApi.bean.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getGroupInfo implements RespBean<Resp_getGroupInfo.GroupInfo> {
    private Integer status;
    private GroupInfo result;
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

    public void setResult(GroupInfo result) {
        this.result = result;
    }

    @Override
    public GroupInfo getResult() {
        return result;
    }

    /*
     {
    "status":0,
    "result":{
        "ac_grade":2,
        "ac_num":34,
        "ac_open":1,
        "app_privilege_flag":98913,
        "auth":0,
        "class":"",
        "classID":10066,
        "ec":0,
        "edu":0,
        "flag":2,
        "gAdmins":[
            11223456,
            11223455
        ],
        "gBoard":"最新公告",
        "gCrtTime":1466098191,
        "gIntro":"群介绍压缩版",
        "gLevel":0,
        "gMaxMem":500,
        "gMemNum":87,
        "gName":"Zero",
        "gOwner":257570,
        "gRIntro":"群介绍完整版",
        "gSpeClass":0,
        "gc":4639,
        "gtype":0,
        "level_def":0,
        "levelname":{
            "lvln1":"潜水",
            "lvln10":"一见倾心",
            "lvln11":"超凡脱俗",
            "lvln12":"风华绝代",
            "lvln13":"崭露头角",
            "lvln14":"金玉满堂",
            "lvln15":"富甲一方",
            "lvln197":"小酋长",
            "lvln198":"大酋长",
            "lvln199":"首席酋长",
            "lvln2":"冒泡",
            "lvln3":"吐槽",
            "lvln4":"活跃",
            "lvln5":"话唠",
            "lvln6":"传说"
        },
        "ns":{
            "11223456":"精神病",
            "11223455":"?????"
        },
        "open":0,
        "photo":{
            "f":0
        },
        "pos":"0|0.100000|0.100000|",
        "search":1,
        "share":{
            "f":0
        },
        "sys_show":1,
        "tags":[
            {
                "md":"0137fe83ea6f849",
                "sTid":"39303163343030303039356433",
                "tag":"你不知道",
                "u":257570
            }
        ],
        "user_show":1
    }
}
        result	object	群信息数组
        result.ac_grade	int	群等级
        result.ac_open	int	推测是加群方式
        result.class	string	推测是群类型
        result.classID	int	推测是群类型ID
        result.ec	int	调用api的结果(相当于状态码)
        result.gAdmins	array	群管理QQ列表
        result.gBoard	string	最新公告内容
        result.gCrtTime	int	建群时间的时间戳
        result.gIntro	string	群介绍(压缩版)
        result.gLevel	int	推测是群等级
        result.gMaxMem	int	群成员上限
        result.gMemNum	int	目前群人数
        result.gName	string	群名
        result.gOwner	number	群主QQ
        result.gRIntro	string	群介绍(完整版)
        result.gc	number	群号
        result.gtype	int	推测是群类型
        result.levelname	object	群成员等级信息
        result.ns	object	群管理和群主的昵称列表
        result.pos	string	推测是群地点信息
        result.search	int	推测是群搜索类型(是否限定通过群号查找)
        result.tags	array	群标签信息列表
        result.tags[i]	object	第i+1个群标签信息
        result.tags[i].md	string	推测是该标签的md5值
        result.tags[i].tag	string	该标签的名称
        result.tags[i].u	number	该标签的创建者QQ
                  */
    public static class GroupInfo {
        private Integer ac_grade;
        private String ac_num;
        private Integer ac_open;
        private String app_privilege_flag;
        private String auth;
        //此处缺少class(不过没啥卵用
        private Integer classID;
        private Integer ec;
        private String edu;
        private String flag;
        private String[] gAdmins;
        private String gBoard;
        private String gCrtTime;
        private String gIntro;
        private Integer gLevel;
        private Integer gMaxMem;
        private Integer gMemNum;
        private String gName;
        private String gOwner;
        private String gRIntro;
        private String gSpeClass;
        private String gc;
        private Integer gtype;
        private String level_def;
        // TODO 群成员等级信息
        private List<Map<String, Object>> levelname = new ArrayList<>();
        // TODO 群管理和群主的昵称列表
        private List<Map<String, Object>> ns = new ArrayList<>();
        private String open;
        private String photo;
        private String pos;
        private Integer search;
        private List<Map<String, Object>> share = new ArrayList<>();
        private String sys_show;
        // TODO  群标签信息列表
        private List<Map<String, Object>> tags = new ArrayList<>();
        private String user_show;

        public Integer getAc_grade() {
            return ac_grade;
        }

        public void setAc_grade(Integer ac_grade) {
            this.ac_grade = ac_grade;
        }

        public String getAc_num() {
            return ac_num;
        }

        public void setAc_num(String ac_num) {
            this.ac_num = ac_num;
        }

        public Integer getAc_open() {
            return ac_open;
        }

        public void setAc_open(Integer ac_open) {
            this.ac_open = ac_open;
        }

        public String getApp_privilege_flag() {
            return app_privilege_flag;
        }

        public void setApp_privilege_flag(String app_privilege_flag) {
            this.app_privilege_flag = app_privilege_flag;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public Integer getClassID() {
            return classID;
        }

        public void setClassID(Integer classID) {
            this.classID = classID;
        }

        public Integer getEc() {
            return ec;
        }

        public void setEc(Integer ec) {
            this.ec = ec;
        }

        public String getEdu() {
            return edu;
        }

        public void setEdu(String edu) {
            this.edu = edu;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String[] getgAdmins() {
            return gAdmins;
        }

        public void setgAdmins(String[] gAdmins) {
            this.gAdmins = gAdmins;
        }

        public String getgBoard() {
            return gBoard;
        }

        public void setgBoard(String gBoard) {
            this.gBoard = gBoard;
        }

        public String getgCrtTime() {
            return gCrtTime;
        }

        public void setgCrtTime(String gCrtTime) {
            this.gCrtTime = gCrtTime;
        }

        public String getgIntro() {
            return gIntro;
        }

        public void setgIntro(String gIntro) {
            this.gIntro = gIntro;
        }

        public Integer getgLevel() {
            return gLevel;
        }

        public void setgLevel(Integer gLevel) {
            this.gLevel = gLevel;
        }

        public Integer getgMaxMem() {
            return gMaxMem;
        }

        public void setgMaxMem(Integer gMaxMem) {
            this.gMaxMem = gMaxMem;
        }

        public Integer getgMemNum() {
            return gMemNum;
        }

        public void setgMemNum(Integer gMemNum) {
            this.gMemNum = gMemNum;
        }

        public String getgName() {
            return gName;
        }

        public void setgName(String gName) {
            this.gName = gName;
        }

        public String getgOwner() {
            return gOwner;
        }

        public void setgOwner(String gOwner) {
            this.gOwner = gOwner;
        }

        public String getgRIntro() {
            return gRIntro;
        }

        public void setgRIntro(String gRIntro) {
            this.gRIntro = gRIntro;
        }

        public String getgSpeClass() {
            return gSpeClass;
        }

        public void setgSpeClass(String gSpeClass) {
            this.gSpeClass = gSpeClass;
        }

        public String getGc() {
            return gc;
        }

        public void setGc(String gc) {
            this.gc = gc;
        }

        public Integer getGtype() {
            return gtype;
        }

        public void setGtype(Integer gtype) {
            this.gtype = gtype;
        }

        public String getLevel_def() {
            return level_def;
        }

        public void setLevel_def(String level_def) {
            this.level_def = level_def;
        }

        public List<Map<String, Object>> getLevelname() {
            return levelname;
        }

        public void setLevelname(List<Map<String, Object>> levelname) {
            this.levelname = levelname;
        }

        public List<Map<String, Object>> getNs() {
            return ns;
        }

        public void setNs(List<Map<String, Object>> ns) {
            this.ns = ns;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public Integer getSearch() {
            return search;
        }

        public void setSearch(Integer search) {
            this.search = search;
        }

        public List<Map<String, Object>> getShare() {
            return share;
        }

        public void setShare(List<Map<String, Object>> share) {
            this.share = share;
        }

        public String getSys_show() {
            return sys_show;
        }

        public void setSys_show(String sys_show) {
            this.sys_show = sys_show;
        }

        public List<Map<String, Object>> getTags() {
            return tags;
        }

        public void setTags(List<Map<String, Object>> tags) {
            this.tags = tags;
        }

        public String getUser_show() {
            return user_show;
        }

        public void setUser_show(String user_show) {
            this.user_show = user_show;
        }
    }

}
