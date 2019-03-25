package com.forte.client.listener.report;

import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.msgget.MsgGroup;
import com.forte.qqrobot.beans.msgget.MsgPrivate;
import com.forte.qqrobot.socket.QQWebSocketMsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 复读助手
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/17 3:01
 * @since JDK1.8
 **/
public class ReportHelper {

    /** 复读记录map key为群号，value[0] 为上一次的消息，value[1] 为这个消息是否已经复读*/
    private static Map<String, Object[]> map = new ConcurrentHashMap<>(1);

    /** 高级记录复读的map，key为群号，value[0]为上一次的消息，value[1]为当前发言人的消息
     *  当出现新的发信人的时候，当前消息人会被移至上次消息人，所以理论上讲上次与当前的消息人的qq不会相同
     *  但是当复读后，当前消息将会被移至上一条消息位置
     * */
    private static Map<String, GroupMsg[]> msgMap = new ConcurrentHashMap<>(1);



    /**
     * 高级复读v1.0
     */
    public static boolean groupReport2(MsgGroup msgGroup, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender){
        //如果是at自己，不复读
        if(at){
            return false;
        }
        //消息
        String msg = msgGroup.getMsg();
        //发信人qq
        String fromQQ = msgGroup.getFromQQ();
        // 群号
        String fromGroup = msgGroup.getFromGroup();

        //记录消息
        saveMsg(fromGroup, fromQQ, msg);

        //记录完成后，判断本次消息与上次的消息是否相同
        return doReportIfNeed(fromGroup, sender);
    }

    /**
     * 获取上一次的消息
     * @param groupCode 群号
     */
    private static GroupMsg getLastGroupMsg(String groupCode){
        return Optional.ofNullable(msgMap.get(groupCode)).map(a -> a[0]).orElseGet(() -> {
            msgMap.putIfAbsent(groupCode, new GroupMsg[2]);
            return null;
        });
    }

    /**
     * 设置上一次的消息
     * @param groupCode 群号
     * @param groupMsg  消息对象
     */
    private static void setLastGroupMsg(String groupCode, GroupMsg groupMsg){
        Optional.ofNullable(msgMap.get(groupCode)).map(a -> {
            a[0] = groupMsg;
            return a;
        }).orElseGet(() -> {
            msgMap.putIfAbsent(groupCode, new GroupMsg[]{groupMsg, null});
            return null;
        });
    }


    /**
     * 获取这一次的消息
     * @param groupCode 群号
     */
    private static GroupMsg getThisGroupMsg(String groupCode){
        return Optional.ofNullable(msgMap.get(groupCode)).map(a -> a[1]).orElseGet(() -> {
            msgMap.putIfAbsent(groupCode, new GroupMsg[2]);
            return null;
        });
    }

    /**
     * 设置这一次的消息
     * @param groupCode 群号
     * @param groupMsg  消息对象
     */
    private static void setThisGroupMsg(String groupCode, GroupMsg groupMsg){
        Optional.ofNullable(msgMap.get(groupCode)).map(a -> {
            a[1] = groupMsg;
            return a;
        }).orElseGet(() -> {
            msgMap.putIfAbsent(groupCode, new GroupMsg[]{null, groupMsg});
            return null;
        });
    }


    /**
     * 记录一条消息
     * @param groupCode 群号
     * @param fromQQ    qq号
     * @param msg       新加的消息内容
     */
    private static void saveMsg(String groupCode, String fromQQ, String msg){
        //记录消息
        GroupMsg[] groupMsgs = msgMap.get(groupCode);
        //如果这个群没有记录
        if(groupMsgs == null){
            //如果没有两次消息，记录新的
            groupMsgs = new GroupMsg[2];
            msgMap.putIfAbsent(groupCode, groupMsgs);
            //消息记录
            GroupMsg groupMsg = new GroupMsg(fromQQ, msg);
            groupMsgs[1] = groupMsg;
        //如果此群有记录，查看记录
        }else{
            //如果群员qq相同，追加记录
            if(groupMsgs[1].groupMemberCode.equals(fromQQ)){
                groupMsgs[1].addMsg(msg);
            }else{
                //如果群员qq不同，将本次记录转移至上次，并记录本次
                groupMsgs[0] = groupMsgs[1];
                groupMsgs[1] = new GroupMsg(fromQQ, msg);
            }
        }
    }


    /**
     * 判断是否需要复读 - 根据群号
     * @return
     */
    private static boolean doReportIfNeed(String groupCode, QQWebSocketMsgSender sender){
        //理论上不会为null，但是保险起见
        GroupMsg[] groupMsgs = msgMap.getOrDefault(groupCode, new GroupMsg[2]);

        GroupMsg lastMsgs = getLastGroupMsg(groupCode);
        GroupMsg thisMsgs = getThisGroupMsg(groupCode);

        //如果其中有null，直接返回false
        if(lastMsgs == null || thisMsgs == null){
            return false;
        }

        //判断当前消息记录是否需要复读
        boolean needReport = thisMsgs.needReport(lastMsgs);
        //如果需要复读，将当前消息放至上一条消息并复读
        if(needReport){
            setLastGroupMsg(groupCode, thisMsgs);
            thisMsgs.doReport(groupCode, sender,900L,  950L);
        }

        return needReport;
    }



    /**
     * 初版复读
     */
    public static boolean groupReport(MsgGroup msgGroup, CQCode[] cqCode, boolean at, CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender){
        //如果是at自己，不复读
        if(at){
            return false;
        }
        String msg = msgGroup.getMsg();
//            System.out.println("当前消息：" + msg);
        String fromGroup = msgGroup.getFromGroup();
//            System.out.println("上一次消息：" + Optional.ofNullable(map.get(fromGroup)).map(o -> o[0]+"").orElse("NULL"));
        //如果是群消息，记录内容
        //如果与上次一样，且没有复读，则复读
        Object[] objects = map.get(fromGroup);
        if (objects == null) {
            //如果不存在，记录
            Object[] re = new Object[2];
            re[0] = msg;
            re[1] = false;
            map.put(fromGroup, re);
        } else {
            //如果存在，判断
            String lastMsg = (String) objects[0];
            Boolean isReport = (Boolean) objects[1];
            //如果当前字符串与上次不一样，记录当前字符串并标记没有复读
            if(!msg.equals(lastMsg)){
                objects[0] = msg;
                objects[1] = false;
            }else{
//                    System.out.println("又是" + msg);
                //如果与上次一样，且没有复读，则复读并标记为已复读
                if(!isReport){
                    sender.sendGroupMsg(fromGroup, msg);
                    objects[1] = true;
                }
            }
        }
        return true;
    }


    /**
     * 私聊复读
     */
    public static boolean privateReport(MsgPrivate msgPrivate, QQWebSocketMsgSender sender){
        //私聊超级智能AI

        String msg = msgPrivate.getMsg();
        String rem = "#\\(\\(您\\)\\)#";

        String remsg = msg
                .replaceAll("虵", "江")
                .replaceAll("我有女朋友吗","没有")
                .replaceAll("吗" , "")
                .replaceAll("\\?","!")
                .replaceAll("？","!")
                .replaceAll(rem ,"你")
                .replaceAll("我",rem)
                .replaceAll("你","我")
                .replaceAll(rem ,"你")
                ;

        String fromQQ = msgPrivate.getFromQQ();
        sender.sendMsgPrivate(fromQQ, remsg);
        return true;

    }

}

/**
 * ——————内部类，记录群员消息
 */
class GroupMsg{
    /** 群员qq号 */
    final String groupMemberCode;
    /** 上次说的话 */
    final List<String> msgs = new ArrayList<>();

    /** 默认的空消息 */
    static final GroupMsg defaultNullMsg = new GroupMsg("00000", "");

    /** 此消息是否已经被复读
     *
     * */
    boolean report = false;
    /**
     * 记录一句话
     * @param msg 消息
     */
    void addMsg(String msg){
        msgs.add(msg);
    }

    /**
     * 判断两个人说的话是否相同
     * @param groupMsg  另一个人说的话记录
     */
    boolean isSame(GroupMsg groupMsg){
        //如果为null，直接返回null
        if(groupMsg == null){
            return false;
        }

        return groupMsg.msgs.equals(this.msgs);
    }

    /**
     * 传入上一次的消息集，判断是否需要复读
     */
    boolean needReport(GroupMsg lastMsg){
        if(lastMsg == null){
            return false;
        }

        //如果上一条消息被复读了，且当前消息与他相同，标记为已复读
        if(lastMsg.isReport() && this.isSame(lastMsg)){
            reported();
            return false;
            //如果上一条被标记为已复读，但是当前消息与上一条不同，标记为未复读
        }else if(lastMsg.isReport() && (!this.isSame(lastMsg))) {
            notReported();
            return false;
            //如果当前消息数量大于1，且小于上一次发言，判断尾部
        }else if((this.msgs.size() > 1) && (this.msgs.size() < lastMsg.msgs.size())){
                //根据长度截取上一次消息的尾部
            List<String> sub = lastMsg.msgs.subList(lastMsg.msgs.size() - this.msgs.size(), lastMsg.msgs.size());

            //如果截取结果与当前消息一致，则需要复读
            if(this.msgs.equals(sub)){
                lastMsg.reported();
                reported();
                return true;
            }else{
                //否则，不复读
                notReported();
                return false;
            }
            //如果当前消息只有一条且与上一条的最后一条相同，也复读
        }else if(this.msgs.size() == 1 && lastMsg.msgs.get(lastMsg.msgs.size() - 1).equals(this.msgs.get(0))){
            lastMsg.reported();
            reported();
            return true;
            //如果上一天消息没有被标记复读，而且当前消息与上一条一样，标记两者为已经复读，返回true
        }else if((!lastMsg.isReport()) && this.isSame(lastMsg)){
            lastMsg.reported();
            reported();
            return true;
            // 否则返回false
        }else return false;
    }

    /**
     * 是否被复读了
     */
    boolean isReport(){
        return report;
    }

    /**
     * 标记为已经复读
     */
    void reported(){
        this.report = true;
    }
    /**
     * 标记为未复读
     */
    void notReported(){
        this.report = false;
    }

    /**
     * 进行复读
     */
    void doReport(String groupCode, QQWebSocketMsgSender sender, Long waitTime){
        doReport(groupCode, sender, 0L, waitTime);
    }

    /**
     * 进行复读
     */
    void doReport(String groupCode, QQWebSocketMsgSender sender, Long firstWait, Long waitTime){
        //先等待
        try {
            Thread.sleep(firstWait);
        } catch (InterruptedException ignore) {
            //who care ?
        }

        //遍历消息
        msgs.forEach(msg -> {
            sender.sendGroupMsg(groupCode, msg);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ignore) {
                //who care ?
            }
        });
    }

    /**
     * 构造一个新的消息记录
     */
    GroupMsg(String groupMemberCode, String msg){
        this.groupMemberCode = groupMemberCode;
        this.msgs.add(msg);
    }
}
