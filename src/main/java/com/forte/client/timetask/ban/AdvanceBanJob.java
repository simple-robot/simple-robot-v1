package com.forte.client.timetask.ban;

import com.forte.client.timetask.MyJob;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 预禁言-提前5分钟提示禁言时间，并说明
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/18 16:41
 * @since JDK1.8
 **/
public class AdvanceBanJob implements MyJob {


    /**
     * 任务逻辑
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        QQWebSocketMsgSender sender = (QQWebSocketMsgSender) jobExecutionContext.getMergedJobDataMap().get("sender");
        CQCodeUtil cqCodeUtil = (CQCodeUtil) jobExecutionContext.getMergedJobDataMap().get("cqCodeUtil");

        int[] times = getTimes();
        TimeStr timeStr = getTimeStr();

        //首先更新禁言时间
        Long banLong = BanUtils.updateBanTime(times);

        //发送提示信息
        sender.sendGroupMsg(GROUP_CODE, "还有5分钟就12点了");

        sleep(1000);

        //展示时间区间字符串
        sender.sendGroupMsg(GROUP_CODE, timeStr.getStr());

        sleep(1000);

        sender.sendGroupMsg(GROUP_CODE, "唔.......");

        sleep(3500);

        sender.sendGroupMsg(GROUP_CODE, "决定了!今天就..."+ banLong +"分钟吧");

        sleep(1000);

        sender.sendGroupMsg(GROUP_CODE, "那么，各位5分钟后见~");

        sleep(1000);

        sender.sendGroupMsg(GROUP_CODE, "顺便一提，如果随机到了管理员的话...只能拜托群主手动禁言啦");
    }

    /**
     * 线程睡眠
     */
    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
    }


    /**
     * 根据时间获取禁言时间
     * @return
     */
    private int[] getTimes(){
        //获取今天在一周的时间
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        int value = dayOfWeek.getValue();

        switch (value){
            case 1 :
            case 2 :
            case 3 :
            case 4 :
            case 5 : return new int[]{value , value * 3};
            case 6 : return new int[]{10 , 30};
            case 7 : return new int[]{5 , 120};
            default: return new int[]{1 , 15};
        }
    }

    /**
     * 根据时间获取禁言时间
     * @return
     */
    private TimeStr getTimeStr(){
        int[] times = getTimes();
        String timesStr = "["+ times[0] +", "+ times[1] +"]";
        //获取今天在一周的时间
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        //当前这个星期的说法
        String nowDayStr = getMonthStr(dayOfWeek);

        String str = "看来今天是" + nowDayStr + "呀，那么我将会在" + timesStr + "(分钟)这个区间中随意获取一个时间来禁言~";

        return new TimeStr(times, str);
    }

    /**
     * 根据星期获取字符串数组
     * @return
     */
    private String[] getMonthStrArr(DayOfWeek dayOfWeek){
        switch (dayOfWeek.getValue()){
            case 1 : return new String[]{"周一" , "礼拜一" , "星期一"};
            case 2 : return new String[]{"周二" , "礼拜二" , "星期二"};
            case 3 : return new String[]{"周三" , "礼拜三" , "星期三"};
            case 4 : return new String[]{"周四" , "礼拜四" , "星期四"};
            case 5 : return new String[]{"周五" , "礼拜五" , "星期五"};
            case 6 : return new String[]{"周六" , "礼拜六" , "星期六"};
            case 7 : return new String[]{"周日" , "礼拜天" , "星期日" , "星期天", "周天", "舒爽的最后一天"};
            default: return new String[]{"未知之日" , "神奇的一天"};
        }
    }

    /**
     * 根据星期获取字符串数组
     * @return
     */
    private String getMonthStr(DayOfWeek dayOfWeek){
        return RandomUtil.getRandomElement(getMonthStrArr(dayOfWeek));
    }

    /**
     * 内部类，封装说的话字符串和禁言时间
     */
    private class TimeStr{
        private final int[] times;
        private final String str;

        public int[] getTimes() {
            return times;
        }

        public String getStr() {
            return str;
        }

        TimeStr(int[] times, String str){
            this.str = str;
            this.times = times;
        }

    }

}
