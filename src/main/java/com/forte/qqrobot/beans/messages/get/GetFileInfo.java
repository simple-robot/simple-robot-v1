package com.forte.qqrobot.beans.messages.get;

import com.forte.qqrobot.beans.messages.result.BanList;
import com.forte.qqrobot.beans.messages.result.FileInfo;

/**
 * 获取群文件信息
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface GetFileInfo extends InfoGet<FileInfo> {
    /**
     * 获取通过此类型请求而获取到的参数的返回值的类型
     */
    @Override
    default Class<? extends FileInfo> resultType(){
        return FileInfo.class;
    }

    String getFlag();

}
