package com.forte.qqrobot.exception;

import com.forte.lang.Language;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.utils.FieldUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/2 15:56
 * @since JDK1.8
 **/
public class RobotRuntimeException extends RuntimeException {

    /**
     * 对于异常类，所有的lang均以exception.开头
     */
    private static final String RUNTIME_ERROR_HEAD = "exception";

    /**
     * 记录所有的子类自动构建出来的二级TAG
     */
    private static final Map<Class, String> ALL_CHILD_CLASS_EXCEPTION_TAG = new HashMap<>(4);

    /** 一般来讲，一个异常类的完整tag开头是：exception.{exception_name}.xxxTag,
     * 而此处则会记录exception.{exception_name}
     * 此值被方法{@link #getExceptionTag()} 初始化，并影响getTag方法的取值。<br>
     *     如果想要自定义二级Tag，即自定义{exception_name}值，则请重写方法{@link #getExceptionTag()} <br>
     *     如果想要自定义整个headTag，即自定义exception.{exception_name}，则请重写方法{@link #getMessageTag()}<br>
     **/
    private String exceptionTag;

    /**
     * 完整的1、2级TAG
     * @see #exceptionTag
     */
    private String messageTag;

    /**
     * 完整的1、2、3级TAG
     */
    private String localizedTag;

    /**
     * 用于格式化输出的参数
     */
    private Object[] tagFormat;

    /**
     * 初始化EXCEPTION_TAG和MESSAGE_TAG
     * 你可以重写此方法以直接禁用此方法或者更改初始化规则
     */
    protected void initTag(Object... format){
        String message = getMessage();
        if (message == null) {
            exceptionTag = null;
            messageTag = null;
            localizedTag = null;
        } else {
            initExceptionTag();
            initMessageTag();
            initLocalizedTag(message);
//            messageTag = RUNTIME_ERROR_HEAD + '.' + exceptionTag;
//            localizedTag =
        }
        tagFormat = format;
    }

    /**
     * 初始化ExceptionTag
     * @return ExceptionTag
     */
    private String initExceptionTag(){
        // 尝试获取缓存
        String exceptionSimpleName = ALL_CHILD_CLASS_EXCEPTION_TAG.get(this.getClass());
        if (exceptionSimpleName == null) {
            exceptionSimpleName = this.getClass().getSimpleName();
            if (exceptionSimpleName.endsWith("Exception")) {
                exceptionSimpleName = exceptionSimpleName.substring(0, exceptionSimpleName.length() - 9);
            }
            // 判断长度是否足够
            if (exceptionSimpleName.length() > 2) {
                // 如果开头是大写，且第二个是小写，则开头转小写。
                if (
                        Character.isUpperCase(exceptionSimpleName.charAt(0))
                                &&
                                Character.isLowerCase(exceptionSimpleName.charAt(1))
                ) {
                    exceptionSimpleName = FieldUtils.headLower(exceptionSimpleName);
                }
            }
            ALL_CHILD_CLASS_EXCEPTION_TAG.put(this.getClass(), exceptionSimpleName);
        }
        exceptionTag = exceptionSimpleName;
        return exceptionTag;
    }

    /**
     * 初始化MessageTag
     * @return messageTag
     */
    private String initMessageTag(){
        return messageTag = RUNTIME_ERROR_HEAD + '.' + getExceptionTag();
    }

    /**
     * 初始化LocalizedTag
     * @param detailMessage see {@link Throwable#getMessage()}
     * @return localizedTag
     */
    private String initLocalizedTag(String detailMessage){
        return localizedTag =  getMessageTag() + '.' + detailMessage;
    }


    /**
     * 异常会通过QQLog.err抛出而不是System.err
     * 实验性质功能
     */
    @Override
    public void printStackTrace() {
        printStackTrace(QQLog.err);
    }

    /**
     * 会获取到
     * <br>
     *     <code>
     *         {@link #RUNTIME_ERROR_HEAD} + '.' + message
     *     </code>
     * <br>
     *     后的转义内容
     * @param message
     * @param format
     * @return
     */
    private static String getMessageFormat(String message, Object... format){
        return Language.format(message, format);
    }

    public static String getRuntimeErrorTagHead(){
        return RUNTIME_ERROR_HEAD;
    }

    /**
     * 默认情况下，会获取类名去掉Exception并开头小写的结果。
     * 例如：BlockException -> block
     * 你可以重写此方法以自定义自己类的二级tag
     */
    public String getExceptionTag(){
        return exceptionTag;
    }

    /**
     * 默认情况下，会获取到
     * <br>
     * <code>
     * {@link #RUNTIME_ERROR_HEAD} + '.' + {@link #exceptionTag}
     * </code>
     * <br>
     * 的结果。<br>
     * 你可以重写此方法以自定义自己的异常类获取的前二级tag
     */
    public String getMessageTag(){
        return messageTag;
    }

    /**
     * 获取一个格式化参数的复制品
     */
    public Object[] getTagFormat(){
        return Arrays.copyOf(tagFormat, tagFormat.length);
    }

    /**
     * 获取一个格式化参数的元素
     * @param index 索引
     * @return 指定索引的元素
     * @throws IndexOutOfBoundsException 有索引越界异常的可能
     */
    public Object getTagFormat(int index) throws IndexOutOfBoundsException {
        return tagFormat[index];
    }

    /**
     * 获取格式化参数的长度
     * @return 指定格式化参数的长度
     */
    public int getTagFormatLength() {
        return tagFormat.length;
    }

    /**
     * 重写本地化描述
     */
    @Override
    public String getLocalizedMessage(){
        String localizedMessageTag = getLocalizedMessageTag();
        if(localizedMessageTag != null){
            return getMessageFormat(localizedMessageTag, tagFormat);
        }else{
            return super.getLocalizedMessage();
        }
    }
    /**
     * @return 本地化描述Tag
     */
    public String getLocalizedMessageTag(){
        return localizedTag;
    }


    /**
     * 获取语言化转化后的消息字符串
     * @return 语言化转化后的消息字符串
     */
    public String getLangMessage(){
        return getLocalizedMessage();
    }

    /**
     * 不再推荐使用此方法。请使用 {@link #getLangMessage()} 方法。
     * @see #getLangMessage()
     */
    @Override
    @Deprecated
    public String getMessage(){
        return super.getMessage();
    }


    public RobotRuntimeException() {
    }

    public RobotRuntimeException(String message, Object... format) {
        super(message);
        initTag(format);
    }

    public RobotRuntimeException(String message) {
        super(message);
        initTag();
    }

    public RobotRuntimeException(String message, Throwable cause, Object... format) {
        super(getMessageFormat(message, format), cause);
        initTag(format);
    }

    public RobotRuntimeException(String message, Throwable cause) {
        super(message, cause);
        initTag();
    }

    public RobotRuntimeException(Throwable cause) {
        super(cause);
    }

    public RobotRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(getMessageFormat(message), cause, enableSuppression, writableStackTrace);
        initTag();
    }

    public RobotRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace);
        initTag(format);
    }


    //**************************************
    //*  提供一部分不会经过消息转化的方法
    //*  这部分方法的第一个参数存在一个int类型的参数，此参数无意义，仅用于标记用以区分其他方法
    //**************************************


    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public RobotRuntimeException(int pointless, String message) {
        super(message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public RobotRuntimeException(int pointless, String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 不进行语言国际化转化的构造方法
     * @param pointless             无意义参数，填任意值 pointless param
     * @param message               信息正文
     * @param cause                 异常
     * @param enableSuppression     whether or not suppression is enabled
     *                                or disabled
     * @param writableStackTrace    whether or not the stack trace should
     *                                 be writable
     */
    public RobotRuntimeException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
