package com.forte.client.timetask;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.RandomUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 禁言工具，用于获取每日的禁言时间
 * 随机区间：1 - 15 分钟
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/18 16:35
 * @since JDK1.8
 **/
public class BanUtils {

    /** 禁言时间，默认为1分钟 */
    private static Long banLong = 1L;

    /** 用于储存禁言记录的地方 */
    private static File banSaveFile = new File("save/banTimes.txt");

    private static String keyValueSplit = "=";
    private static String dataSplit = ";";

    private static final String ENCODING = "UTF-8";

    /** 禁言次数 */
    private static final Map<String, Integer> banTimesMap;

    //加载禁言次数记录
    static{
        banTimesMap = new HashMap<>(10);
        //读取文件
        try {
            String read = FileUtils.readFileToString(banSaveFile, ENCODING);

            //切割
            for (String data : read.split(dataSplit)) {
                String[] kv = data.split(keyValueSplit);
                String k = kv[0];
                String v = kv[1];
                banTimesMap.put(k, Integer.parseInt(v));
            }

        } catch (IOException ignore) {
            //如果文件不存在,报错,那又怎样？
        }
    }

    /**
     * 重新随机禁言时间
     */
    public static Long updateBanTime(int a, int b){
        banLong = Integer.toUnsignedLong(RandomUtil.getNumber$right(a, b));
        return banLong;
    }

    /**
     * 重新随机禁言时间
     */
    public static Long updateBanTime(int[] arr){
        banLong = Integer.toUnsignedLong(RandomUtil.getNumber$right(arr[0], arr[1]));
        return banLong;
    }

    public static long getBanLong(){
        return banLong;
    }

    /**
     * 增加某人的禁言次数记录
     */
    public static void updateBanSave(String qqCode){
        Integer integer = banTimesMap.get(qqCode);
        if(integer == null){
            banTimesMap.putIfAbsent(qqCode, 1);
        }else{
            banTimesMap.put(qqCode, integer+1);
        }

        //使用新线程进行文件更新
        ResourceDispatchCenter.getThreadPool().execute(BanUtils::updateFile);
    }

    /**
     * 更新记录文件
     */
    private static void updateFile(){
        String datas = banTimesMap.entrySet().stream().map(e -> e.getKey() + keyValueSplit + e.getValue()).collect(Collectors.joining(dataSplit));
        try {
            FileUtils.writeStringToFile(banSaveFile, datas, ENCODING, false);
        } catch (IOException e) {
            QQLog.debug("禁言文件记录失败");
        }
    }


}



