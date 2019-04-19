package com.forte.forhttpapi.beans.request.get;

import com.forte.forhttpapi.beans.response.Resp_getRecord;

/**
 * 「接收语音文件」
 *  需要权限30
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 17:13
 * @since JDK1.8
 **/
public class Req_getRecord implements ReqGetBean<Resp_getRecord> {

    private final String fun = "getRecord";

    /** 文件名，必须是消息中的语音文件(file) */
    private String source;
    /** 目标编码，默认MP3, 目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac */
    private RecordType format = RecordType.mp3;
    /** 是否回传文件数据，true/回传，false/不回传 */
    private Boolean needFile;

    @Override
    public String getFun() {
        return fun;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public RecordType getFormat() {
        return format;
    }

    public void setFormat(RecordType format) {
        this.format = format;
    }

    public Boolean getNeedFile() {
        return needFile;
    }

    public void setNeedFile(Boolean needFile) {
        this.needFile = needFile;
    }

    @Override
    public Class<Resp_getRecord> getResponseType() {
        return Resp_getRecord.class;
    }

    /**
     * ——————————————目标编码类型
     */
    public enum RecordType{
        mp3("mp3"),
        amr("amr"),
        wma("wma"),
        m4a("m4a"),
        spx("spx"),
        ogg("ogg"),
        wav("wav"),
        flac("flac");

        private String type;

        @Override
        public String toString(){
            return type;
        }
        RecordType(String type){
            this.type = type;
        }
    }
}
