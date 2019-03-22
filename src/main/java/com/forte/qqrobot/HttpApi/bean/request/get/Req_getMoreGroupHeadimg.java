package com.forte.qqrobot.HttpApi.bean.request.get;

import com.forte.qqrobot.HttpApi.bean.request.ReqBean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 「批量取群头像」
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:07
 * @since JDK1.8
 **/
public class Req_getMoreGroupHeadimg implements ReqBean {

    private final String fun = "getMoreGroupHeadimg";
    /** 群列表，每个群用-分开，可空，空时表示取所有群的头像链接 */
    private String groupList;

    @Override
    public String getFun() {
        return fun;
    }

    public String getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        this.groupList = groupList;
    }

    public void setGroupList(String[] groupList) {
        this.setGroupList(String.join("-", groupList));
    }

    public void setGroupList(List<String> groupList) {
        this.setGroupList(String.join("-", groupList));
    }
}
