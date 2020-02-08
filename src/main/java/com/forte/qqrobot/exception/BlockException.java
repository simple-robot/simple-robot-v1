package com.forte.qqrobot.exception;

/**
 * 阻断异常
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/2 15:53
 * @since JDK1.8
 **/
public class BlockException extends RobotException {

    /**
     * 此异常下的中间语言tag，即exception.block.xxx
     */
    private static final String LANG_TAG_HEAD = "block";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BlockException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BlockException(String message) {
        super(LANG_TAG_HEAD + '.' + message);
    }
}
