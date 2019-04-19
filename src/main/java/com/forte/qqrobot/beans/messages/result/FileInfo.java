package com.forte.qqrobot.beans.messages.result;

/**
 * 群文件信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface FileInfo extends InfoResult {

    /** 获取文件名称 */
    String getFileName();

    /** 文件ID */
    String getId();

    /** 文件BUSID */
    String getBusid();

    /** 获取文件大小-字节 */
    Long getFileSize();


}
