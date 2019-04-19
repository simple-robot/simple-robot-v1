package com.forte.qqrobot.beans.messages.result;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ImageInfo extends InfoResult {

    /** 取图片的MD5值 */
    String getMD5();

    /** 图片宽 */
    Double getWidth();
    /** 图片长 */
    Double getHeight();

    /** 获取图片大小 字节 */
    Long getSize();

    /** 图片路径 */
    String getUrl();
    /** 图片上传到腾讯时候的时间 */
    Long getTime();

    /** 图片Base64编码内容 */
    String getFileBase64();

}
