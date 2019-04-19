package com.forte.qqrobot.http;

/**
 * 当接收了消息之后，经过处理后要进行响应的消息的封装类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/15 16:40
 * @since JDK1.8
 **/
public class Resp {

    /** responseHeader 响应类型 第一个参数 默认为200 成功 */
    private int headerLeft = 200;

    /** responseHeader 响应类型 第二个参数，不知道是干啥的 默认为0 */
    private long headerRight = 0L;

    /** responseBody 响应正文 默认为[] */
    private String body = "[]";

    public int getHeaderLeft() {
        return headerLeft;
    }

    public void setHeaderLeft(int headerLeft) {
        this.headerLeft = headerLeft;
    }

    public long getHeaderRight() {
        return headerRight;
    }

    public void setHeaderRight(long headerRight) {
        this.headerRight = headerRight;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
