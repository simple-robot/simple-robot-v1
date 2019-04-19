package com.forte.forlemoc.beans.inforeturn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Arrays;
import java.util.Optional;

/**
 * 获取指定QQ号所在的所有群id和群名称
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 10:49
 * @since JDK1.8
 **/
public class ReturnLoginInGroups implements InfoReturn {

    /** 返回标记编码, 应该为 25305 */
    @JSONField(name = "return")
    private Integer returnCode;
    /** 错误码 为-1表示出错，为0表示正常。*/
    private Integer error;
    /** QQID为你发送的指定QQ号，以确定是对哪一条消息的响应, 理论上讲无论是什么群列表都为本机QQ列表 */
    private String QQID;
    /**
     * GroupList为一个json列表，示例如下，groupId为群号，groupName为群名称：
     * [
     *   {"groupId": "1111","groupName": "黑手吹逼群"},
     *   {"groupId": "2222","groupName": "屁股围观群"},
     *   {"groupId": "3333","groupName": "法鸡粉丝群"}
     * ]
     */
    private String GroupList;

    /** 群列表信息 */
    private GroupInfo[] groupInfos;

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public String getGroupList() {
        return GroupList;
    }

    public void setGroupList(String groupList) {
        GroupList = groupList;
    }

    public GroupInfo[] getGroupInfos() {
        if(groupInfos == null){
            groupInfos = Optional.ofNullable(this.GroupList).map(listStr -> JSON.parseArray(listStr).stream().map(str -> new GroupInfo(str.toString())).toArray(GroupInfo[]::new)).orElse(new GroupInfo[0]);
        }
        return this.groupInfos;
    }

    public void setReturn(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public Integer getReturn() {
        return this.returnCode;
    }

    /**
     * 内部类，储存了群列表信息
     * [
     *  {"groupId": "1111","groupName": "黑手吹逼群"},
     *  {"groupId": "2222","groupName": "屁股围观群"},
     *  {"groupId": "3333","groupName": "法鸡粉丝群"}
     *]
     */
    public class GroupInfo{
        /** 群号 */
        private final String groupId;
        /** 群名 */
        private final String groupName;

        /** 构造 */
        GroupInfo(String jsonData){
            JSONObject data = JSON.parseObject(jsonData);
            this.groupId = data.getString("groupId");
            this.groupName = data.getString("groupName");
        }

        public String getGroupId() {
            return groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        @Override
        public String toString() {
            return "GroupInfo{" +
                    "groupId='" + groupId + '\'' +
                    ", groupName='" + groupName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReturnLoginInGroups{" +
                "returnCode=" + returnCode +
                ", error=" + error +
                ", QQID='" + QQID + '\'' +
                ", GroupList='" + GroupList + '\'' +
                ", groupInfos=" + Arrays.toString(groupInfos) +
                '}';
    }
}
