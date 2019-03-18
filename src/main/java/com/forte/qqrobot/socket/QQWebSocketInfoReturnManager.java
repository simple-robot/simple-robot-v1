package com.forte.qqrobot.socket;

import com.forte.qqrobot.beans.inforeturn.InfoReturn;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 返回信息管理器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 11:42
 * @since JDK1.8
 **/
public class QQWebSocketInfoReturnManager {

    /** 获取并保存返回的信息，共5种返回值信息 */
    private static Map<Integer, AtomicReference<InfoReturnBean>> infoReturnMap = new ConcurrentHashMap<>(5);

    /** 对应的请求的最后一次请求时间 */
    private static Map<Integer, AtomicLong> lastSendTime = new ConcurrentHashMap<>(5);

    /**
     * 标记一次发送的请求
     * @param act   请求的act码
     */
    public void send(int act){
        //当前时间时间戳
        long now = Instant.now().toEpochMilli();

        //最后一次请求的时间
        AtomicLong atomicLong = lastSendTime.get(act);
        if(atomicLong == null){
            //如果不存在最后一次请求时间，保存
            atomicLong = new AtomicLong(now);
            lastSendTime.putIfAbsent(act, atomicLong);
        }

        //如果最后请求时间小于当前时间，更新时间
        atomicLong.updateAndGet(l -> now>l ? now : l);
    }


    /**
     * 更新一次数据
     * @param act           act码
     * @param infoReturn    infoReturn对象
     */
    void update(int act, InfoReturn infoReturn){
        //当前时间戳
        long now = Instant.now().toEpochMilli();
        AtomicReference<InfoReturnBean> lastUpdateInfo = infoReturnMap.get(act);
        //如果不存在，创建
        if(lastUpdateInfo == null){
            lastUpdateInfo = new AtomicReference<>(new InfoReturnBean(infoReturn, now));
            infoReturnMap.putIfAbsent(act, lastUpdateInfo);
        }

        //更新
        lastUpdateInfo.updateAndGet(i -> i.getReturnTime() < now ? new InfoReturnBean(infoReturn, now) : i);
    }


    /**
     * 获取
     * @param act           act码
     * @return              infoReturn对象
     */
    public InfoReturn get(int act){
        InfoReturn infoReturn = null;

        do{
            //持续获取，直到最后一次请求时间小于最后一次获取使时间

            InfoReturnBean infoReturnBean = infoReturnMap.get(act).get();
            //获取两个时间
            Long lastUpdateTime = infoReturnMap.get(act).get().returnTime;
            Long lastSend = lastSendTime.get(act).get();
            //如果最后更新时间大于最后请求时间，则认为获取到了
            if(lastUpdateTime >= lastSend){
                infoReturn = infoReturnBean.getInfoReturn();
            }

        }while(infoReturn == null);
        return infoReturn;
    }


    /**
     * 内部类，记录了返回信息的封装类
     */
    private class InfoReturnBean {
        /** 接收到此消息的时间戳 */
        private Long returnTime;
        /** 接收到的InfoReturn对象 */
        private InfoReturn infoReturn;



        Long getReturnTime() {
            return returnTime;
        }

        void setReturnTime(Long returnTime) {
            this.returnTime = returnTime;
        }

        InfoReturn getInfoReturn() {
            return infoReturn;
        }

        void setInfoReturn(InfoReturn infoReturn) {
            this.infoReturn = infoReturn;
        }

        /** 构造 */
        InfoReturnBean(InfoReturn infoReturn, Long returnTime){
            this.infoReturn = infoReturn;
            this.returnTime = returnTime;
        }

    }
}
