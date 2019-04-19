package com.forte.forlemoc.beans.infoget;

/**
 * 25305,获取指定QQ号所在的所有群id和群名称
 * QQID(此QQID仅为标记用，无论填写的QQ为多少，返回的群列表都是酷Q当前登录QQ号所在的群)
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:32
 * @since JDK1.8
 **/
public class InfoForLoginInGroups implements InfoGet {
    /*
    25305,获取指定QQ号所在的所有群id和群名称
    QQID(此QQID仅为标记用，无论填写的QQ为多少，返回的群列表都是酷Q当前登录QQ号所在的群)
    返回json串字段：
    error，act，return，QQID，GroupList
     */
    /** 编号 */
    private static final Integer act = 25305;
    /** qq号 (此QQID仅为标记用，无论填写的QQ为多少，返回的群列表都是酷Q当前登录QQ号所在的群) */
    private String QQID;



    public Integer getAct() {
        return act;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }
}
