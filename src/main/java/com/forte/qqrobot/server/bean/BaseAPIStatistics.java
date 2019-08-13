package com.forte.qqrobot.server.bean;

import com.alibaba.fastjson.JSON;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * highchartsAPI统计数据封装类基类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseAPIStatistics implements APIStatistics {

    /** 大标题 */
    protected abstract String getTitle();
    /** x轴标题 */
    protected abstract String getYTitle();
    /** API对应的名称，例如SENDER */
    protected abstract String getAPIName();
    /** 各个API对应的使用次数 */
    protected abstract Map<String, Double> getStatisticDatas();

    /** 图标类型 */
    private String type = "bar";
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }


    /*
        {
             chart: {
                 type: 'bar'                          //指定图表的类型，默认是折线图（line）
             },
             title: {
                 text: 'ALL-API使用量统计'                 // 标题
             },
             xAxis: {
                 categories: ['SENDER-API']   // x 轴分类
             },
             yAxis: {
                 title: {
                     text: 'SENDER-API各使用次数统计'                // y 轴标题
                 }
             },
             series: [{                              // 数据列
                 name: 'sendPrivateMsg',             // 数据列名
                 data: [1]                     // 数据
             }, {
                 name: 'SendGroupMsg',
                 data: [5]
             }, {
                 name: 'SendLink',
                 data: [5]
             }]
         }
     */

    @Override
    public String toJson(){
        //开始构建JSON字符串 - 使用fastJSON进行构建
        Map<String, Object> data = new HashMap<>(8);
        data.put("chart", new AbstractMap.SimpleEntry<>("type", getType()));
        data.put("title", new AbstractMap.SimpleEntry<>("text", getTitle()));
        data.put("xAxis", new AbstractMap.SimpleEntry<>("categories", new String[]{getAPIName()}));

        Map<String, Double> statisticDatas = getStatisticDatas();
        Map[] dataMaps = statisticDatas.entrySet().stream().map(e -> new HashMap<String, Object>(2) {{
            put("name", e.getKey());
            put("data", new double[]{e.getValue()});
        }}).toArray(Map[]::new);

        data.put("series", dataMaps);
        return JSON.toJSONString(data);
    }

    @Override
    public String toString(){
        return this.toJson();
    }

}
