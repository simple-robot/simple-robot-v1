package com.forte.qqrobot.test

import com.forte.qqrobot.beans.messages.msgget.GroupMsg
import com.forte.qqrobot.beans.messages.types.GroupMsgType
import com.forte.qqrobot.beans.messages.types.PowerType

class TestGroupMsg: GroupMsg {
    private var QQ: String? = null
    fun setQQ(QQ: String){this.QQ = QQ}
    private var group: String? = null
    private var type: GroupMsgType? = GroupMsgType.NORMAL_MSG
    private var id: String? = null
    private var msg: String? = null
    private var font: String? = null
    private var time: Long? = System.currentTimeMillis()
    private var originalData: String? = "{\"originalData\": \"test msg\"}"
    private var thisCode: String? = null

    /** 获取群消息发送人的qq号  */
    override fun getQQ(): String? = QQ

    /** 获取群消息的群号  */
    override fun getGroup(): String? = group

    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     * @return 接收到此消息的账号。
     */
    override fun getThisCode(): String? = thisCode

    /** 获取ID, 一般用于消息类型判断  */
    override fun getId(): String? = id

    /**
     * 获取此人在群里的权限
     * @return 权限，例如群员、管理员等
     */
    override fun getPowerType(): PowerType? = powerType

    /**
     * 一般来讲，监听到的消息大部分都会有个“消息内容”。定义此方法获取消息内容。
     * 如果不存在，则为null。（旧版本推荐为空字符串，现在不了。我变卦了）
     */
    override fun getMsg(): String? = msg

    /**
     * 重新定义此人的权限
     * @param powerType 权限
     */
    override fun setPowerType(powerType: PowerType?) {this.powerType = powerType}

    /** 获取原本的数据 originalData  */
    override fun getOriginalData(): String? = originalData

    /** 获取消息类型  */
    override fun getType(): GroupMsgType? = type

    /**
     * 重新设置消息
     * @param newMsg msg
     * @since 1.7.x
     */
    override fun setMsg(newMsg: String?) {this.msg = newMsg}

    /** 获取到的时间, 代表某一时间的秒值。一般情况下是秒值。如果类型不对请自行转化  */
    override fun getTime(): Long? = time

    /** 获取消息的字体  */
    override fun getFont(): String? = font

    /**
     * 允许重新定义Code以实现在存在多个机器人的时候切换处理。
     * @param code code
     */
    override fun setThisCode(code: String?) {this.thisCode = code}


}