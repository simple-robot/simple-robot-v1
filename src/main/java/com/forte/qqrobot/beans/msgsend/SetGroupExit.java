package com.forte.qqrobot.beans.msgsend;

/**
 * 127, 置群退出(出于安全起见，该权限没有开启)
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 11:58
 * @since JDK1.8
 **/
public class SetGroupExit {
    /*
    127, 置群退出(出于安全起见，该权限没有开启)
    groupid，isdismiss
     */

    /** 编码 */
    private final Integer act = 127;
    /** 群号 */
    private String groupid;
    /** 大概是是否退群..? */
    private Boolean isdismiss;



    public Integer getAct() {
        return act;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Boolean getIsdismiss() {
        return isdismiss;
    }

    public void setIsdismiss(Boolean isdismiss) {
        this.isdismiss = isdismiss;
    }
}
