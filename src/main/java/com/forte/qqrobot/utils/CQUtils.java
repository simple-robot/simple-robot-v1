package com.forte.qqrobot.utils;

import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.exception.ConfigurationException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * CQ相关工具类 <br>
 * ※ 应定义好CQ根路径
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CQUtils {

    /** 图片文件夹路径 */
    private static final String IMAGE_PATH = "/data/image";

    /** cq图片文件保存的文件格式 */
    private static final String CQ_IMAGE_TYPE = "cqimg";


    /**
     * 获取图片保存文件夹
     */
    public static String getImagePath(){
        return getImageFolderFile().getAbsolutePath();
    }


    /**
     * 获取图片保存文件夹路径
     */
    public static File getImageFolderFile(){
        return new File(getRootPath(), IMAGE_PATH);
    }

    /**
     * 获取指定图片文件路径
     */
    public static String getImageFilePath(String fileId){
        return getImageFile(fileId).getAbsolutePath();
    }

    /**
     * 获取图片文件对象
     */
    public static File getImageFile(String fileId){
        return new File(getRootPath(), IMAGE_PATH + "/" + fileId + "." + CQ_IMAGE_TYPE);
    }

    /**
     * 获取根路径
     * @throws ConfigurationException 如果没有配置酷q路径，抛出此异常
     */
    private static String getRootPath(){
        return Optional.ofNullable(BaseConfiguration.getCqPath()).orElseThrow(() -> new ConfigurationException("您未配置酷Q根路径！"));
    }

    /**
     * 如果路径结尾处有斜杠，移除掉
     */
    private static String toPath(String rootPath){
        return ( (rootPath.endsWith("/")) || (rootPath.endsWith("\\")) ) ? rootPath.substring(0, rootPath.length() - 1) : rootPath;
    }

}
