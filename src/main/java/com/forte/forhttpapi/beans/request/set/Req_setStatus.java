package com.forte.forhttpapi.beans.request.set;

import com.forte.forhttpapi.beans.request.ReqBean;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/22 18:11
 * @since JDK1.8
 **/
public class Req_setStatus implements ReqBean {

    private final String fun = "setStatus";

    /** 数据内容 */
    private String data;
    /** 数据单位 */
    private String unit;
    /** 颜色，1/绿 2/橙 3/红 4/深红 5/黑 6/灰 */
    private StatusColor color;


    @Override
    public String getFun() {
        return fun;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public StatusColor getColor() {
        return color;
    }

    public void setColor(StatusColor color) {
        this.color = color;
    }


    /**
     * ——————————内部枚举，定义颜色类型
     */
    public enum StatusColor{
        /** 绿 */
        green (1),
        /** 橙 */
        Orange (2),
        /** 红 */
        red (3),
        /** 深红*/
        Crimson (4),
        /** 黑 */
        black (5),
        /** 灰 */
        gray (6);

        private final int type;

        public int getType(){
            return this.type;
        }


        StatusColor(int type){
            this.type = type;
        }

    }

}
