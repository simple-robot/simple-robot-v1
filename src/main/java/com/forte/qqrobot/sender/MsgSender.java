package com.forte.qqrobot.sender;

import com.forte.lang.Language;
import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.cqcode.AppendList;
import com.forte.qqrobot.beans.cqcode.CQAppendList;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.DiscussMsg;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.result.GroupInfo;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.beans.messages.result.StrangerInfo;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.exception.NoSuchBlockNameException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.sender.intercept.SenderGetIntercept;
import com.forte.qqrobot.sender.intercept.SenderInterceptFactory;
import com.forte.qqrobot.sender.intercept.SenderSendIntercept;
import com.forte.qqrobot.sender.intercept.SenderSetIntercept;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 送信器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgSender implements Sender {

    /**
     * 使用此连接管理器的是哪个监听方法
     */
    private final ListenerMethod LISTENER_METHOD;

    /**
     * 1.8.0后，应当存在一个Runtime对象
     */
    private final BotRuntime runtime;

    /**
     * 消息发送器-send
     */
    public final SenderSendList SENDER;

    /**
     * 消息发送器-set
     */
    public final SenderSetList SETTER;

    /**
     * 消息发送器-get
     */
    public final SenderGetList GETTER;


    private static SenderSendIntercept[] senderSendIntercepts = {};
    private static SenderSetIntercept[] senderSetIntercepts = {};
    private static SenderGetIntercept[] senderGetIntercepts = {};

    //**************** 送信器的拦截代理 ****************//

    /**
     * 设置send送信器拦截
     */
    public static void setSenderSendIntercepts(SenderSendIntercept... senderSendIntercepts) {
        MsgSender.senderSendIntercepts = senderSendIntercepts;
    }

    /**
     * 设置set送信器拦截
     */
    public static void setSenderSetIntercepts(SenderSetIntercept... senderSetIntercepts) {
        MsgSender.senderSetIntercepts = senderSetIntercepts;
    }

    /**
     * 设置get送信器拦截
     */
    public static void setSenderGetIntercepts(SenderGetIntercept... senderGetIntercepts) {
        MsgSender.senderGetIntercepts = senderGetIntercepts;
    }

    //**************** 账号相关 ****************//

    /**
     * 获取一个指定的Bot对象
     *
     * @param botCode botCode
     * @return {@link BotInfo} bot信息
     */
    public BotInfo bot(String botCode) {
        return runtime.getBotManager().getBot(botCode);
    }

    /**
     * 获取一个默认的Bot对象
     *
     * @return {@link BotInfo} bot信息
     */
    public BotInfo bot() {
        return runtime.getBotManager().defaultBot();
    }

    /**
     * 获取一个指定bot的送信器
     *
     * @param botCode bot账号
     * @return bot送信器
     */
    public BotSender botSender(String botCode) {
        return bot(botCode).getSender();
    }

    /**
     * 获取默认的Bot送信器
     *
     * @return bot送信器
     */
    public BotSender botSender() {
        return bot().getSender();
    }


    //**************** 判断相关 ****************//

    /**
     * 是否存在监听器函数
     */
    @Override
    public boolean hasMethod() {
        return LISTENER_METHOD != null;
    }

    /**
     * 是否存在send消息器
     */
    @Override
    public boolean isSendAble() {
        return SENDER != null;
    }

    /**
     * 是否存在set消息器
     */
    @Override
    public boolean isSetAble() {
        return SETTER != null;
    }

    /**
     * 是否存在Get消息器
     *
     * @return
     */
    @Override
    public boolean isGetAble() {
        return GETTER != null;
    }

    //**************** 阻断相关 ****************//

    /**
     * 获取阻断器
     */
    private Plug getPlug() {
        return ResourceDispatchCenter.getPlug();
    }

    /**
     * 获取空阻断器
     */
    private Plug getEmptyPlug() {
        return Plug.EmptyPlug.build();
    }

    /**
     * 根据是否存在监听函数来获取阻断器
     */
    private Plug getPlugByMethod() {
        return hasMethod() ? getPlug() : getEmptyPlug();
    }

    //**************************************
    //*     额外参数的获取，也可以视为方法加强
    //**************************************

    /**
     * 通过QQ号获取陌生人信息
     *
     * @param code qq号
     * @return qq号的信息
     */
    public StrangerInfo getPersonInfoByCode(String code) {
        return GETTER.getStrangerInfo(code);
    }

    /**
     * 通过携带QQ号信息的对象来获取信息
     *
     * @param codeAble 携带QQ号信息的对象
     * @return
     */
    public StrangerInfo getPersonInfo(QQCodeAble codeAble) {
        return getPersonInfoByCode(codeAble.getQQCode());
    }

    /**
     * 通过群号获取群详细信息
     *
     * @param groupCode 群号
     * @return 群详细信息
     */
    public GroupInfo getGroupInfoByCode(String groupCode) {
        return GETTER.getGroupInfo(groupCode);
    }

    /**
     * 通过携带群号的对象获取群详细信息
     *
     * @param groupCodeAble 携带群号的对象
     * @return 群详细信息
     */
    public GroupInfo getGroupInfo(GroupCodeAble groupCodeAble) {
        return getGroupInfoByCode(groupCodeAble.getGroupCode());
    }

    /**
     * 获取酷q上的qq信息
     *
     * @return qq信息
     */
    public LoginQQInfo getLoginInfo() {
        return GETTER.getLoginQQInfo();
    }


    /**
     * 快速回复，根据传入的类型判断。
     * 只支持{@link PrivateMsg}、{@link GroupMsg}、{@link DiscussMsg}三种类型。
     *
     * @param msg   接收到的消息
     * @param reply 回复的正文
     * @param at    是否要at他，默认为true，只有群消息和私信消息生效
     */
    public void reply(MsgGet msg, String reply, boolean at) {
        if (msg instanceof PrivateMsg) {
            SENDER.sendPrivateMsg((PrivateMsg) msg, reply);
        } else if (msg instanceof GroupMsg) {
            GroupMsg gm = (GroupMsg) msg;
            if(at){
                reply = "[CQ:at,qq="+ gm.getQQ() +"] " + reply;
            }
            SENDER.sendPrivateMsg(gm, reply);
        } else if (msg instanceof DiscussMsg) {
            DiscussMsg gm = (DiscussMsg) msg;
            if(at){
                reply = "[CQ:at,qq="+ gm.getQQ() +"] " + reply;
            }
            SENDER.sendPrivateMsg(gm, reply);
        } else {
            String err = Language.format("msgSender.reply.failed", msg.getClass());
            throw new IllegalArgumentException(err);
        }

    }


    //**************************************
    //*             构建工厂
    //**************************************


    /**
     * 是所有工厂方法的汇总方法之一
     */
    public static MsgSender build(SenderList senderList, ListenerMethod listenerMethod, BotRuntime runtime) {
        return (senderList == null && listenerMethod == null) ? buildEmpty(runtime)
                :
                (listenerMethod == null ?
                        new NoListenerMsgSender(senderList, runtime)
                        :
                        new MsgSender(senderList, listenerMethod, runtime)
                );
    }

    //**************** 全 ****************//

    /**
     * 是所有工厂方法的汇总方法之一
     */
    public static MsgSender build(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod, BotRuntime runtime) {
        return (sender == null && setter == null && getter == null && listenerMethod == null) ?
                buildEmpty(runtime)
                :
                (listenerMethod == null ?
                        new NoListenerMsgSender(sender, setter, getter, runtime)
                        :
                        new MsgSender(sender, setter, getter, listenerMethod, runtime)
                );
    }

    //**************** 全 ****************//

    public static MsgSender build(SenderSendList sender, SenderSetList setter, SenderGetList getter, BotRuntime runtime) {
        return build(sender, setter, getter, null, runtime);
    }


    //**************** 空 ****************//

    public static MsgSender buildEmpty(BotRuntime runtime) {
        return new MsgSender(null, null, runtime);
    }


    //**************** 构造 ****************//

    /**
     * 构造
     */
    private MsgSender(SenderList senderList, ListenerMethod listenerMethod, BotRuntime runtime) {
        this(senderList, listenerMethod, runtime, true, true, true);
    }

    /**
     * 构造
     */
    private MsgSender(SenderList senderList, ListenerMethod listenerMethod, BotRuntime runtime,
                      boolean interceptSend, boolean interceptSet, boolean interceptGet) {
        this.runtime = runtime;
        //如果为空，直接赋值为null
        if (senderList == null) {
            this.SENDER = null;
            this.SETTER = null;
            this.GETTER = null;
        } else {
            //构建SENDER
            this.SENDER = senderList.isSenderList() ? (interceptSend ? initSender((SenderSendList) senderList) : (SenderSendList) senderList) : null;
            this.SETTER = senderList.isSetterList() ? (interceptSet ? initSetter((SenderSetList) senderList) : (SenderSetList) senderList) : null;
            this.GETTER = senderList.isGetterList() ? (interceptGet ? initGetter((SenderGetList) senderList) : (SenderGetList) senderList) : null;
        }

        //为listenerMethod赋值
        this.LISTENER_METHOD = initListener(listenerMethod);
    }

    /**
     * 构造
     */
    protected MsgSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod, BotRuntime runtime) {
        this(sender, setter, getter, listenerMethod, runtime, true, true, true);
    }

    /**
     * 构造
     */
    protected MsgSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod, BotRuntime runtime,
                        boolean interceptSend, boolean interceptSet, boolean interceptGet) {
        this.runtime = runtime;
        //构建SENDER, 如果存在拦截器，则构建代理
        this.SENDER = interceptSend ? initSender(sender) : sender;
        this.SETTER = interceptSet ? initSetter(setter) : setter;
        this.GETTER = interceptGet ? initGetter(getter) : getter;

        //为listenerMethod赋值
        this.LISTENER_METHOD = initListener(listenerMethod);
    }

    /**
     * 初始化sender
     */
    private SenderSendList initSender(SenderSendList sender) {
        if (senderSendIntercepts != null && senderSendIntercepts.length > 0) {
            return SenderInterceptFactory.doSenderIntercept(sender, senderSendIntercepts);
        } else {
            return sender;
        }
    }

    /**
     * 初始化setter
     */
    private SenderSetList initSetter(SenderSetList setter) {
        if (senderSetIntercepts != null && senderSetIntercepts.length > 0) {
            return SenderInterceptFactory.doSetterIntercept(setter, senderSetIntercepts);
        } else {
            return setter;
        }
    }

    /**
     * 初始化setter
     */
    private SenderGetList initGetter(SenderGetList getter) {
        if (senderGetIntercepts != null && senderGetIntercepts.length > 0) {
            return SenderInterceptFactory.doGetterIntercept(getter, senderGetIntercepts);
        } else {
            return getter;
        }
    }

    /**
     * 初始化所在监听函数
     */
    private ListenerMethod initListener(ListenerMethod method) {
        return method;
    }


    //**************************************
    //*             阻塞机制 默认值均为替换
    //**************************************

//    /** 阻断中，是否替换的默认值-false */
//    private static final boolean DEFAULT_APPEND = false;

    //**************** 普通阻塞 ****************//

    //**************************************
    //* 所有与开启阻塞相关的，需要将监听函数作为参数的阻断方法
    //* 都需要使用getPlugByMethod方法来获取
    //* 基本上来讲，有返回值的使用真实阻断器，无返回值的使用判断
    //**************************************

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     */
    @Override
    public void onBlockOnlyThis(boolean append) {
        getPlugByMethod().onBlockByMethod(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     * 默认替换
     */
    @Override
    public void onBlockOnlyThis() {
        onBlockOnlyThis(DEFAULT_APPEND);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     */
    @Override
    public void onBlockByThisName(boolean append) {
        getPlugByMethod().onBlockByName(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     * 默认替换
     */
    @Override
    public void onBlockByThisName() {
        onBlockByThisName(DEFAULT_APPEND);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     * 此方法当前执行的监听函数没有关联
     */
    @Override
    public void onBlockByName(String name, boolean append) {
        getPlugByMethod().onBlock(name, append);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     * 默认替换
     */
    @Override
    public void onBlockByName(String name) {
        getPlugByMethod().onBlock(name, DEFAULT_APPEND);
    }

    /**
     * 取消普通阻塞-即清空阻塞函数容器
     * 此方法使用真实的阻断器
     */
    @Override
    public void unBlock() {
        //获取阻断器
        getPlug().unBlock();
    }

    /**
     * 移除全局阻塞
     */
    @Override
    public void unGlobalBlock() {
        getPlug().unGlobalBlock();
    }

    /**
     * 取消全部阻塞
     * 此方法使用真实的阻断器
     */
    @Override
    public void unAllBlock() {
        Plug plug = getPlug();
        plug.unGlobalBlock();
        plug.unBlock();
    }


    //**************** 全局阻塞 ****************//

    /**
     * 根据一个名称更新全局阻塞
     */
    @Override
    public void onGlobalBlockByName(String name) {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(name);
    }

    /**
     * 根据阻断名称的索引来更新全局阻塞
     */
    @Override
    public void onGlobalBlockByNameIndex(int index) throws NoSuchBlockNameException {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(this.LISTENER_METHOD, index);
    }

    /**
     * 根据第一个阻断名称来更新全剧阻塞
     */
    @Override
    public void onGlobalBlockByFirstName() {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(this.LISTENER_METHOD);
    }


    //**************** 获取阻断器部分信息 ****************//

    /**
     * 根据组名判断自己所在的组是否全部在阻断状态中
     */
    @Override
    public boolean isAllOnBlockByName() {
        return getPlugByMethod().isAllOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否有任意在阻断状态中
     */
    @Override
    public boolean isAnyOnBlockByName() {
        return getPlugByMethod().isAnyOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否全部没有在阻断状态中
     */
    @Override
    public boolean isNoneOnBlockByName() {
        return getPlugByMethod().isNoneOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否存在于阻断队列
     */
    @Override
    public boolean isOnBlock() {
        return getPlugByMethod().isOnNormalBlockByThis(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否作为单独的阻断被阻断了
     */
    @Override
    public boolean isOnlyThisOnBlock() {
        return getPlugByMethod().osOnNormalBlockByOnlyThis(this.LISTENER_METHOD);
    }

    //**************** 获取阻断状态的两个方法使用真实阻断器 ****************//

    /**
     * 获取当前处于全局阻断状态下的阻断组名
     *
     * @return 阻断组名
     */
    @Override
    public String getOnGlobalBlockName() {
        return getPlug().getGlobalBlockName();
    }

    /**
     * 获取当前处于普通阻断状态下的阻断组名列表
     *
     * @return 处于普通阻断状态下的阻断组名列表
     */
    @Override
    public String[] getOnNormalBlockNameArray() {
        return getPlug().getNormalBlockNameArray();
    }


    /**
     * 无监听函数的送信器, 便于区分
     */
    @Deprecated
    public static class NoListenerMsgSender extends MsgSender {

        private NoListenerMsgSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, BotRuntime runtime) {
            super(sender, setter, getter, null, runtime);
        }

        public NoListenerMsgSender(SenderList senderList, BotRuntime runtime) {
            super(senderList, null, runtime);
        }
    }


}
