package com.forte.qqrobot.beans.messages.result;

/**
 * InfoResult对应抽象类
 * @see InfoResult
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractInfoResult implements InfoResult {

    private String originalData;

    @Override
    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    @Override
    public String toString() {
        return "InfoResult{" +
                "originalData='" + getOriginalData() + '\'' +
                '}';
    }
}
