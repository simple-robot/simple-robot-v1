package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * 「删除群文件」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:54
 * @since JDK1.8
 **/
public class Req_setGroupFileDelete implements ReqBean {

    private final String fun = "setGroupFileDelete";
    private String model = "api";


    /** 群号 */
    private String group;
    /** 未知作用，值为群文件信息中的busid */
    private Integer busid;
    /** 文件ID，值为群文件信息中的id */
    private String id;

    @Override
    public String getFun() {
        return fun;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getBusid() {
        return busid;
    }

    public void setBusid(Integer busid) {
        this.busid = busid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
