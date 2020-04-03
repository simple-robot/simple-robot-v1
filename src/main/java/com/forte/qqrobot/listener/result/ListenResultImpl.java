package com.forte.qqrobot.listener.result;

/**
 *
 * {@link ListenResult} 的抽象类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ListenResultImpl<T> implements ListenResult<T> {

    /**
     * 排序值
     */
    private int sort;

    /**
     * 结果值
     */
    private T result;

    /**
     * 是否判定为成功
     */
    private Boolean success;

    private Boolean toBreak;

    private Boolean toBreakPlugin;

    private Exception error;


    public static <T> ListenResult<T> result(int sort, T result, boolean success, boolean toBreak, boolean toBreakPlugin, Exception error){
        return new ListenResultImpl<>(sort, result, success, toBreak, toBreakPlugin, error);
    }

    public static <T> ListenResult<T> resultBreak(int sort, T result, boolean success, boolean toBreakPlugin, Exception error){
        return new ListenResultImpl<>(sort, result, success, true, toBreakPlugin, error);
    }

    public static <T> ListenResult<T> resultEmpty(int sort,  boolean success,  boolean toBreak, boolean toBreakPlugin, Exception error){
        return new ListenResultImpl<>(sort, null, success, toBreak, toBreakPlugin, error);
    }

    //**************** 没有throwable参数的 ****************//

    public static <T> ListenResult<T> result(int sort, T result, boolean success, boolean toBreak, boolean toBreakPlugin){
        return new ListenResultImpl<>(sort, result, success, toBreak, toBreakPlugin, null);
    }

    public static <T> ListenResult<T> resultBreak(int sort, T result, boolean success, boolean toBreakPlugin){
        return new ListenResultImpl<>(sort, result, success, true, toBreakPlugin, null);
    }

    public static <T> ListenResult<T> resultEmpty(int sort,  boolean success,  boolean toBreak, boolean toBreakPlugin){
        return new ListenResultImpl<>(sort, null, success, toBreak, toBreakPlugin, null);
    }


    /**
     * 基础的全参构造
     * @param sort      排序值
     * @param result    返回值
     * @param success   成功与否
     */
    public ListenResultImpl(int sort, T result, boolean success, boolean toBreak, boolean toBreakPlugin, Exception error){
        this.sort = sort;
        this.result = result;
        this.success = success;
        this.toBreak = toBreak;
        this.toBreakPlugin = toBreakPlugin;
        this.error = error;
    }
    /** 无参构造 */
    public ListenResultImpl(){ }





    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public Boolean isSuccess() {
        return success;
    }

    @Override
    public Boolean isToBreak() {
        return getToBreak();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public T result() {
        return getResult();
    }

    @Override
    public int sortValue() {
        return getSort();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getToBreak() {
        return toBreak;
    }

    public void setToBreak(Boolean toBreak) {
        this.toBreak = toBreak;
    }

    public Boolean getToBreakPlugin() {
        return toBreakPlugin;
    }

    @Override
    public Boolean isToBreakPlugin(){
        return getToBreakPlugin();
    }

    public void setToBreakPlugin(Boolean toBreakPlugin) {
        this.toBreakPlugin = toBreakPlugin;
    }

    @Override
    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

}
