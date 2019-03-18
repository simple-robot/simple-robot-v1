package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.beans.inforeturn.*;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 18:19
 * @since JDK1.8
 **/
public enum InfoReturnTypes {

    /** ReturnGroupMember类型 */
    returnGroupMember(25303, ReturnGroupMember.class),
    /** ReturnLoginInGroups类型 */
    returnLoginInGroups(25305, ReturnLoginInGroups.class),
    /** ReturnLoginNick类型 */
    returnLoginNick(25302, ReturnLoginNick.class),
    /** ReturnLoginQQ类型 */
    returnLoginQQ(25301, ReturnLoginQQ.class),
    /** ReturnStranger类型 */
    returnStranger(25304, ReturnStranger.class),

    ;

    /**
     * 通过return码获取返回信息类型
     * @param returnCode   返回码
     * @return  InfoReturnTypes对象
     */
    public static InfoReturnTypes getInfoReturnTypesByReturn(int returnCode){
        switch (returnCode){
            case 25303: return returnGroupMember;
            case 25305: return returnLoginInGroups;
            case 25302: return returnLoginNick;
            case 25301: return returnLoginQQ;
            case 25304: return returnStranger;
            default: return null;
        }
    }


    /** 返回值编码 */
    private final int returnCode;
    /** 具体的class类 */
    private final Class<? extends InfoReturn> returnClass;

    /**
     * 构造
     *
     * @param returnCode  响应编码
     * @param returnClass class对象
     */
    InfoReturnTypes(int returnCode, Class<? extends InfoReturn> returnClass) {
        this.returnCode = returnCode;
        this.returnClass = returnClass;
    }

}
