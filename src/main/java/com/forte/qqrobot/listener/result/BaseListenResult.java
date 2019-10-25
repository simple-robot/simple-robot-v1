package com.forte.qqrobot.listener.result;

/**
 *
 * {@link ListenResult} 的抽象类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BaseListenResult<T> implements ListenResult<T> {

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


    /**
     * 基础的全参构造
     * @param sort      排序值
     * @param result    返回值
     * @param success   成功与否
     */
    public BaseListenResult(int sort, T result, boolean success){
        this.sort = sort;
        this.result = result;
        this.success = success;
    }



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

    @Override
    public int compareTo(ListenResult<T> o) {
        return Integer.compare(o.sortValue(), sortValue());
    }
}
