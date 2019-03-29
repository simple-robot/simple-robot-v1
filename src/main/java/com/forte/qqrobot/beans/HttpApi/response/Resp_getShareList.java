package com.forte.qqrobot.beans.HttpApi.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getShareList implements RespBean<Resp_getShareList.ShareList[]> {
    private Integer status;
    private ShareList[] result;
    private String errMsg;

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(ShareList[] result) {
        this.result = result;
    }

    @Override
    public ShareList[] getResult() {
        return result;
    }

    /*
{
    "status":0,
    "result":[
        {
            "auditflag":1,
            "busid":102,
            "createtime":1501217864,
            "downloadtimes":10,
            "filelenhight":0,
            "filelenlow":550027,
            "filename":"聊天.zip",
            "filepath":"/102/a26d8444-4abb-4fdd-0000-39999717f03c",
            "filesize":550427,
            "localname":"",
            "modifytime":1501217865,
            "ownernick":"н",
            "owneruin":20000,
            "ttl":0,
            "uploadlenhigh":0,
            "uploadlenlow":550427,
            "uploadnick":"н",
            "uploadsize":550427,
            "uploaduin":20000
        }
    ]
}
result	array	文件信息
result[i]	object	第i+1个文件信息
result[i].busid	int	BUSID
result[i].createtime	int	创建时间，时间戳形式
result[i].downloadtimes	int	下载次数
result[i].filename	string	文件名
result[i].filepath	string	在QQ服务器上的文件路径
result[i].filesize	number	文件大小，单位：字节(B)
result[i].localname	string	本地文件名
result[i].modifytime	int	上传完成时间
result[i].ownernick	string	上传者的群名片
result[i].owneruin	number	上传者的QQ
result[i].uploadlenlow	number	上传的文件大小
result[i].uploadnick	string	上传者的群名片
result[i].uploadsize	number	上传的文件大小
result[i].uploaduin	number	上传者的QQ
     */
    public static class ShareList {
        private String auditflag;
        private Integer busid;
        private String createtime;
        private Integer downloadtimes;
        private String filelenhight;
        private String filelenlow;
        private String filename;
        private String filepath;
        private Long filesize;
        private String localname;
        private String modifytime;
        private String ownernick;
        private String owneruin;
        private String ttl;
        private String uploadlenhigh;
        private Integer uploadlenlow;
        private String uploadnick;
        private Integer uploadsize;
        private String uploaduin;

        public String getAuditflag() {
            return auditflag;
        }

        public void setAuditflag(String auditflag) {
            this.auditflag = auditflag;
        }

        public Integer getBusid() {
            return busid;
        }

        public void setBusid(Integer busid) {
            this.busid = busid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Integer getDownloadtimes() {
            return downloadtimes;
        }

        public void setDownloadtimes(Integer downloadtimes) {
            this.downloadtimes = downloadtimes;
        }

        public String getFilelenhight() {
            return filelenhight;
        }

        public void setFilelenhight(String filelenhight) {
            this.filelenhight = filelenhight;
        }

        public String getFilelenlow() {
            return filelenlow;
        }

        public void setFilelenlow(String filelenlow) {
            this.filelenlow = filelenlow;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public Long getFilesize() {
            return filesize;
        }

        public void setFilesize(Long filesize) {
            this.filesize = filesize;
        }

        public String getLocalname() {
            return localname;
        }

        public void setLocalname(String localname) {
            this.localname = localname;
        }

        public String getModifytime() {
            return modifytime;
        }

        public void setModifytime(String modifytime) {
            this.modifytime = modifytime;
        }

        public String getOwnernick() {
            return ownernick;
        }

        public void setOwnernick(String ownernick) {
            this.ownernick = ownernick;
        }

        public String getOwneruin() {
            return owneruin;
        }

        public void setOwneruin(String owneruin) {
            this.owneruin = owneruin;
        }

        public String getTtl() {
            return ttl;
        }

        public void setTtl(String ttl) {
            this.ttl = ttl;
        }

        public String getUploadlenhigh() {
            return uploadlenhigh;
        }

        public void setUploadlenhigh(String uploadlenhigh) {
            this.uploadlenhigh = uploadlenhigh;
        }

        public Integer getUploadlenlow() {
            return uploadlenlow;
        }

        public void setUploadlenlow(Integer uploadlenlow) {
            this.uploadlenlow = uploadlenlow;
        }

        public String getUploadnick() {
            return uploadnick;
        }

        public void setUploadnick(String uploadnick) {
            this.uploadnick = uploadnick;
        }

        public Integer getUploadsize() {
            return uploadsize;
        }

        public void setUploadsize(Integer uploadsize) {
            this.uploadsize = uploadsize;
        }

        public String getUploaduin() {
            return uploaduin;
        }

        public void setUploaduin(String uploaduin) {
            this.uploaduin = uploaduin;
        }
    }


}
