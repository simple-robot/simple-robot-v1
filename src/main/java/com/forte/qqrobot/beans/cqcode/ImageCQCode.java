package com.forte.qqrobot.beans.cqcode;

import com.forte.qqrobot.beans.types.CQCodeTypes;
import com.forte.qqrobot.exception.CQParamsException;
import com.forte.qqrobot.exception.CQParseException;
import com.forte.qqrobot.utils.CQUtils;
import com.forte.qqrobot.utils.StringListReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 核心框架提供的imageCQ类型的增加对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ImageCQCode extends CQCode {

    /** 本类对象固定为image类型 */
    private static final CQCodeTypes CQ_CODE_TYPE = CQCodeTypes.image;

    /** 图片文件地址 */
    private final File IMAGE_FILE;

    /** 编码格式 */
    private final String ENCODING = "utf-8";

    //**************** CQIMG的一些参数 ****************//
    /** MD5值 */
    private final String MD5;
    /** 宽度 */
    private final double WIDTH;
    /** 长度 */
    private final double HEIGHT;
    /** 文件大小 */
    private final long SIZE;
    /** 图片网络路径 */
    private final String URL;

    private final long ADD_TIME;

    /**
     * 私有构造 为了测试暂时的公共构造
     * @param params 参数列表
     */
    private ImageCQCode(Map<String, String> params) throws IOException {
        super(CQ_CODE_TYPE, params);
        //获取文件md5参数
        String fileId = params.get("file");
        if(fileId == null){
            throw new CQParamsException("无法获取参数[file]");
        }
        //解析参数以获取图片地址
        this.IMAGE_FILE = CQUtils.getImageFile(fileId);

        //获取文件中的参数并赋值
        List<String> datas = FileUtils.readLines(this.IMAGE_FILE, this.ENCODING);
        //使用properties接收参数，则将字符串集合转化为Reader流对象

                                                //跳过第一行的数据
        Reader listReader = new StringListReader(datas.stream().skip(1).collect(Collectors.toList()));

        //加载参数
        Properties properties = new Properties();
        properties.load(listReader);

        //将参数赋值
        /*
            [image]
            md5=0A02C3998CC3AF7F390D1D8608441637
            width=1009
            height=1794
            size=225910
            url=https://gchat.qpic.cn/gchatpic_new/2209278137/581250423-2837957861-0A02C3998CC3AF7F390D1D8608441637/0?vuin=2257290268&term=2
            addtime=1555648320
     */
        MD5 = properties.getProperty("md5");
        WIDTH = Double.parseDouble(properties.getProperty("width"));
        HEIGHT = Double.parseDouble(properties.getProperty("height"));
        SIZE = Long.valueOf(properties.getProperty("size"));
        URL = properties.getProperty("url");
        ADD_TIME = Long.parseLong(properties.getProperty("addtime"));
    }

    /**
     * 将CQCode对象转化为ImageCQCode对象
     * @param cqCode    cqCode对象
     * @return          ImageCqCode对象
     */
    public static ImageCQCode of(CQCode cqCode) throws IOException {
        if(!cqCode.getCQCodeTypes().equals(CQ_CODE_TYPE)){
            throw new CQParseException("无法将["+ cqCode.getCQCodeTypes() +"]类型的CQ码对象转化为ImageCQCode类型");
        }
        //如果本身就是ImageCQCode对象，直接转化
        if(cqCode instanceof ImageCQCode){
            return (ImageCQCode) cqCode;
        }

        return of(cqCode.getParams());
    }

    public static ImageCQCode of(Map<String, String> params) throws IOException {
        return new ImageCQCode(params);
    }



    public File getImageFile() {
        return IMAGE_FILE;
    }

    public String getMD5() {
        return MD5;
    }

    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }

    public long getSize() {
        return SIZE;
    }

    public String getURL() {
        return URL;
    }

    public long getADdTime() {
        return ADD_TIME;
    }
}
