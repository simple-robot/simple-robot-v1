package com.forte.qqrobot.beans.messages.result;

/**
 * @see ImageInfo
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractImageInfo extends AbstractInfoResult implements ImageInfo {
    private String MD5;
    private Double width;
    private Double height;
    private Long size;
    private String url;
    private Long time;
    private String fileBase64;

    @Override
    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    @Override
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Override
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    @Override
    public String toString() {
        return "AbstractImageInfo{" +
                "MD5='" + getMD5() + '\'' +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", size=" + getSize() +
                ", url='" + getUrl() + '\'' +
                ", time=" + getTime() +
                ", fileBase64='" + getFileBase64() + '\'' +
                "} " + super.toString();
    }
}
