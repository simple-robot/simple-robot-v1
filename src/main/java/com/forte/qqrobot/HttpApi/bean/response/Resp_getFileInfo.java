package com.forte.qqrobot.HttpApi.bean.response;

/**
 * @author Ricardo
 * @create 2019-03-22 16:44
 **/

public class Resp_getFileInfo implements RespBean<Resp_getFileInfo.FileInfo> {

    private Integer status;
    private FileInfo result;

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResult(FileInfo result) {
        this.result = result;
    }

    @Override
    public FileInfo getResult() {
        return result;
    }


    /*
        {
        "status":0,
        "result":{
            "fun":"getFileInfo",
            "source":"ACUAAGAY=",
            "size":40207871,
            "busid":102,
            "name":"com.kuaikan.comic-1.apk",
            "id":"/a04e806e-2b5e-480b-923a-a0d99a33ad9d"
        }
    }
         result	object	文件信息数组
         result.size	number	文件大小，单位：字节(B)
         result.busid	int	文件的BUSID
         result.name	string	文件名
         result.id	string	文件ID
         */
    class FileInfo {
        private String fun;
        private String source;
        private Long size;
        private Integer busid;
        private String name;
        private String id;

        public String getFun() {
            return fun;
        }

        public void setFun(String fun) {
            this.fun = fun;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public Integer getBusid() {
            return busid;
        }

        public void setBusid(Integer busid) {
            this.busid = busid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
