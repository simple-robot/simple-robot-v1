package com.forte.qqrobot.sender;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.GroupInfo;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.beans.messages.result.StrangerInfo;
import com.forte.qqrobot.exception.NoSuchBlockNameException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.qqrobot.listener.invoker.plug.Plug;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

/**
 * 送信器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MsgSender implements Sender{

    /**
     * 使用此连接管理器的是哪个监听方法
     */
    private final ListenerMethod LISTENER_METHOD;

    /** 消息发送器-send */
    public final SenderSendList SENDER;

    /** 消息发送器-set */
    public final SenderSetList SETTER;

    /** 消息发送器-get */
    public final SenderGetList GETTER;

    /** 送信器与监听函数都是空的常量对象 */
    private static final MsgSender EMPTY_SENDER = new MsgSender(null, null);

    //**************** 判断相关 ****************//

    /**
     * 是否存在监听器函数
     */
    public boolean hasMethod() {
        return LISTENER_METHOD != null;
    }

    /**
     * 是否存在send消息器
     */
    public boolean isSendAble(){
        return SENDER == null;
    }

    /**
     * 是否存在set消息器
     */
    public boolean isSetAble(){
        return SETTER == null;
    }

    /**
     * 是否存在Get消息器
     * @return
     */
    public boolean isGetAble(){
        return GETTER == null;
    }

    //**************** 阻断相关 ****************//

    /**
     * 获取阻断器
     */
    private Plug getPlug(){
        return ResourceDispatchCenter.getPlug();
    }

    /**
     * 获取空阻断器
     */
    private Plug getEmptyPlug(){
        return Plug.EmptyPlug.build();
    }

    /**
     * 根据是否存在监听函数来获取阻断器
     */
    private Plug getPlugByMethod(){
        return hasMethod() ? getPlug() : getEmptyPlug();
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
    public void onBlockOnlyThis(boolean append) {
        getPlugByMethod().onBlockByMethod(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 仅仅添加这一个，不根据名称关联其他
     * 默认替换
     */
    public void onBlockOnlyThis() {
        onBlockOnlyThis(DEFAULT_APPEND);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     */
    public void onBlockByThisName(boolean append) {
        getPlugByMethod().onBlockByName(this.LISTENER_METHOD, append);
    }

    /**
     * 开启阻塞-普通阻塞
     * 根据当前函数的阻塞名称添加全部同名函数
     * 默认替换
     */
    public void onBlockByThisName() {
        onBlockByThisName(DEFAULT_APPEND);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     * 此方法当前执行的监听函数没有关联
     */
    public void onBlockByName(String name, boolean append) {
        getPlugByMethod().onBlock(name, append);
    }

    /**
     * 根据组名来使某个分组进入阻断状态
     * 默认替换
     */
    public void onBlockByName(String name) {
        getPlugByMethod().onBlock(name, DEFAULT_APPEND);
    }

    /**
     * 取消普通阻塞-即清空阻塞函数容器
     * 此方法使用真实的阻断器
     */
    public void unBlock() {
        //获取阻断器
        getPlug().unBlock();
    }

    /**
     * 移除全局阻塞
     */
    public void unGlobalBlock() {
        getPlug().unGlobalBlock();
    }

    /**
     * 取消全部阻塞
     * 此方法使用真实的阻断器
     */
    public void unAllBlock() {
        Plug plug = getPlug();
        plug.unGlobalBlock();
        plug.unBlock();
    }


    //**************** 全局阻塞 ****************//

    /**
     * 根据一个名称更新全局阻塞
     */
    public void onGlobalBlockByName(String name) {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(name);
    }

    /**
     * 根据阻断名称的索引来更新全局阻塞
     */
    public void onGlobalBlockByNameIndex(int index) throws NoSuchBlockNameException {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(this.LISTENER_METHOD, index);
    }

    /**
     * 根据第一个阻断名称来更新全剧阻塞
     */
    public void onGlobalBlockByFirstName() {
        //获取阻断器
        getPlugByMethod().onGlobalBlock(this.LISTENER_METHOD);
    }


    //**************** 获取阻断器部分信息 ****************//

    /**
     * 根据组名判断自己所在的组是否全部在阻断状态中
     */
    public boolean isAllOnBlockByName() {
        return getPlugByMethod().isAllOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否有任意在阻断状态中
     */
    public boolean isAnyOnBlockByName() {
        return getPlugByMethod().isAnyOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 根据组名判断自己所在的组是否全部没有在阻断状态中
     */
    public boolean isNoneOnBlockByName() {
        return getPlugByMethod().isNoneOnNormalBlockByName(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否存在于阻断队列
     */
    public boolean isOnBlock() {
        return getPlugByMethod().isOnNormalBlockByThis(this.LISTENER_METHOD);
    }

    /**
     * 判断自己是否作为单独的阻断被阻断了
     */
    public boolean isOnlyThisOnBlock() {
        return getPlugByMethod().osOnNormalBlockByOnlyThis(this.LISTENER_METHOD);
    }

    //**************** 获取阻断状态的两个方法使用真实阻断器 ****************//

    /**
     * 获取当前处于全局阻断状态下的阻断组名
     *
     * @return 阻断组名
     */
    public String getOnGlobalBlockName() {
        return getPlug().getGlobalBlockName();
    }

    /**
     * 获取当前处于普通阻断状态下的阻断组名列表
     *
     * @return 处于普通阻断状态下的阻断组名列表
     */
    public String[] getOnNormalBlockNameArray() {
        return getPlug().getNormalBlockNameArray();
    }


    //**************************************
    //*     额外参数的获取，也可以视为方法加强
    //**************************************

    /**
     * 通过QQ号获取陌生人信息
     * @param code  qq号
     * @return      qq号的信息
     */
    public StrangerInfo getPersonInfoByCode(String code){
        return GETTER.getStrangerInfo(code);
    }

    /**
     * 通过携带QQ号信息的对象来获取信息
     * @param codeAble  携带QQ号信息的对象
     * @return
     */
    public StrangerInfo getPersonInfo(QQCodeAble codeAble){
        return getPersonInfoByCode(codeAble.getQQCode());
    }

    /**
     * 通过群号获取群详细信息
     * @param groupCode 群号
     * @return  群详细信息
     */
    public GroupInfo getGroupInfoByCode(String groupCode){
        return GETTER.getGroupInfo(groupCode);
    }

    /**
     *  通过携带群号的对象获取群详细信息
     * @param groupCodeAble 携带群号的对象
     * @return  群详细信息
     */
    public GroupInfo getGroupInfo(GroupCodeAble groupCodeAble){
        return getGroupInfoByCode(groupCodeAble.getGroupCode());
    }

    /**
     * 获取酷q上的qq信息
     * @return qq信息
     */
    public LoginQQInfo getLoginInfo(){
        return GETTER.getLoginQQInfo();
    }



    //**************************************
    //*             构建工厂
    //**************************************


    /**
     * 是所有工厂方法的汇总方法之一
     */
    public static MsgSender build(SenderList senderList, ListenerMethod listenerMethod){
        return (senderList == null && listenerMethod == null) ? buildEmpty() : new MsgSender(senderList, listenerMethod);
    }

    //**************** 全 ****************//

    /**
     * 是所有工厂方法的汇总方法之一
     */
    public static MsgSender build(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod){
        return  (sender == null && setter == null && getter == null && listenerMethod == null) ?
                buildEmpty()
                :
                new MsgSender(sender, setter, getter, listenerMethod);
    }


    //**************** 有listenerMethod ****************//

    //**************** 成对儿 ****************//

    public static MsgSender build(SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod){
        return build(null, setter, getter, listenerMethod);
    }
    public static MsgSender build(SenderSendList sender, SenderSetList setter, ListenerMethod listenerMethod){
        return build(sender, setter, null, listenerMethod);
    }
    public static MsgSender build(SenderSendList sender, SenderGetList getter, ListenerMethod listenerMethod){
        return build(sender, null, getter, listenerMethod);
    }

    //**************** 单个 ****************//

    public static MsgSender build(SenderSendList sender, ListenerMethod listenerMethod){
        return build(sender, null, null, listenerMethod);
    }
    public static MsgSender build(SenderSetList setter, ListenerMethod listenerMethod){
        return build(null, setter, null, listenerMethod);
    }
    public static MsgSender build(SenderGetList getter, ListenerMethod listenerMethod){
        return build(null, null, getter, listenerMethod);
    }

    //**************** 没有listenerMethod ****************//

    public static MsgSender build(SenderList senderList){
        return build(senderList, null);
    }

    //**************** 全 ****************//

    public static MsgSender build(SenderSendList sender, SenderSetList setter, SenderGetList getter){
        return build(sender, setter, getter, null);
    }

    //**************** 成对儿 ****************//

    public static MsgSender build(SenderSetList setter, SenderGetList getter){
        return build(null, setter, getter, null);
    }
    public static MsgSender build(SenderSendList sender, SenderSetList setter){
        return build(sender, setter, null, null);
    }
    public static MsgSender build(SenderSendList sender, SenderGetList getter){
        return build(sender, null, getter, null);
    }

    //**************** 单个 ****************//

    public static MsgSender buildOnlySender(SenderSendList sender){
        return build(sender, null, null, null);
    }
    public static MsgSender buildOnlySetter(SenderSetList setter){
        return build(null, setter, null, null);
    }
    public static MsgSender buildOnlyGetter(SenderGetList getter){
        return build(null, null, getter, null);
    }


    //**************** 空 ****************//

    public static MsgSender buildEmpty(){
        return EMPTY_SENDER;
    }




    //**************** 构造 ****************//


    /** 构造 */
    private MsgSender(SenderList senderList, ListenerMethod listenerMethod){
        //如果为空，直接赋值为null
        if(senderList == null){
            this.SENDER = null;
            this.SETTER = null;
            this.GETTER = null;
        }else{
            //构建SENDER
            this.SENDER = senderList.isSenderList() ? (SenderSendList)senderList : null;
            this.SETTER = senderList.isSetterList() ? (SenderSetList) senderList : null;
            this.GETTER = senderList.isGetterList() ? (SenderGetList) senderList : null;
        }

        //为listenerMethod赋值
        this.LISTENER_METHOD = listenerMethod;
    }


    /** 构造 */
    private MsgSender(SenderSendList sender, SenderSetList setter, SenderGetList getter, ListenerMethod listenerMethod){
            //构建SENDER
            this.SENDER = sender;
            this.SETTER = setter;
            this.GETTER = getter;
        //为listenerMethod赋值
        this.LISTENER_METHOD = listenerMethod;
    }

}
