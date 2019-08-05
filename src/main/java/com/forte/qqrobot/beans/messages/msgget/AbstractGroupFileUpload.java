package com.forte.qqrobot.beans.messages.msgget;

/**
 * GroupFileUpload对应抽象类
 * @see GroupFileUpload
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractGroupFileUpload extends AbstractEventGet implements GroupFileUpload {

    private String group;

    private String qq;

    private String fileName;

    private Long fileSize;

    private String fileBusid;

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String getQQ() {
        return qq;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String getFileBusid() {
        return fileBusid;
    }

    public void setFileBusid(String fileBusid) {
        this.fileBusid = fileBusid;
    }

    @Override
    public String toString() {
        return "GroupFileUpload{" +
                "group='" + getGroup() + '\'' +
                ", qq='" + getQQ() + '\'' +
                ", fileName='" + getFileName() + '\'' +
                ", fileSize=" + getFileSize() +
                ", fileBusid='" + getFileBusid() + '\'' +
                "} " + super.toString();
    }
}
