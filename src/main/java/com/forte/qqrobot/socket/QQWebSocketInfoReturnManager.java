package com.forte.qqrobot.socket;

import com.forte.qqrobot.beans.inforeturn.InfoReturn;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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

    /** 所有返回值的缓冲列表 */
    private static Map<Integer, ConcurrentLinkedQueue<InfoReturnBean>> returnLinkedMap = new ConcurrentHashMap<>(5);

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
     * @param act           act码，也是接收到的消息的return对象信息
     * @param infoReturn    infoReturn对象
     */
    void update(int act, InfoReturn infoReturn){
        //当前时间戳
        long now = Instant.now().toEpochMilli();
        //将更新的数据放入缓冲区
        ConcurrentLinkedQueue<InfoReturnBean> returnLink = returnLinkedMap.get(act);

        //如果没有，创建
        if(returnLink == null){
            returnLink = new ConcurrentLinkedQueue<>();
            returnLink.add(new InfoReturnBean(infoReturn, now));
            returnLinkedMap.putIfAbsent(act, returnLink);
        }else{
            //如果存在，将返回结果放至尾部
            returnLink.add(new InfoReturnBean(infoReturn, now));
        }

//        AtomicReference<InfoReturnBean> lastUpdateInfo = infoReturnMap.get(act);
//        //如果不存在，创建
//        if(lastUpdateInfo == null){
//            lastUpdateInfo = new AtomicReference<>(new InfoReturnBean(infoReturn, now));
//            infoReturnMap.putIfAbsent(act, lastUpdateInfo);
//        }
//
//        //更新
//        lastUpdateInfo.updateAndGet(i -> i.getReturnTime() < now ? new InfoReturnBean(infoReturn, now) : i);
    }


    /**
     * 获取
     * @param act           act码
     * @return              infoReturn对象
     */
    public synchronized InfoReturn get(int act){
        InfoReturnBean infoReturnBean = null;

        do{
            //从缓冲队列中持续获取，理论上缓冲队列不会为null
            ConcurrentLinkedQueue<InfoReturnBean> infoB = returnLinkedMap.get(act);
            if(infoB != null){
                //从缓冲队列中获取
                infoReturnBean = infoB.poll();
            }

            //持续获取缓冲区的首部
        }while(infoReturnBean == null);

        return infoReturnBean.getInfoReturn();
    }


    /**
     * 获取
     * @param act           act码
     * @return              infoReturn对象
     */
    public <T extends InfoReturn> T get(int act, Class<T> infoReturnClass){
        return (T) get(act);
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
