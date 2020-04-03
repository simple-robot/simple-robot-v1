package com.forte.qqrobot.server.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * SENDER的API统计
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SenderAPIStatistics extends BaseAPIStatistics {

    private static final String title = "SENDER-API使用量统计";
    private static final String yTitle = "SENDER-API各使用次数统计";
    private static final String APIName = "SENDER-API";

    /**
     * 大标题
     */
    @Override
    protected String getTitle() {
        return title;
    }

    /**
     * x轴标题
     */
    @Override
    protected String getYTitle() {
        return yTitle;
    }

    /**
     * API对应的名称，例如SENDER
     */
    @Override
    protected String getAPIName() {
        return APIName;
    }

    /**
     * 各个API对应的使用次数
     */
    @Override
    protected Map<String, Double> getStatisticDatas() {
        Random r = new Random();
        return new LinkedHashMap<String, Double>(){{
            put("SendPrivateMsg", r.nextInt(200)*1.0);
            put("SendGroupMsg", r.nextInt(200)*1.0);
            put("SendLike", r.nextInt(200)*1.0);
        }};
    }
}
