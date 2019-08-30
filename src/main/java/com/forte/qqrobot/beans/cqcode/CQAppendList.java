package com.forte.qqrobot.beans.cqcode;

import com.forte.qqrobot.beans.types.CQCodeTypes;
import com.forte.qqrobot.exception.CQParseException;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 多个CQCode或者字符串信息的链接体
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CQAppendList implements AppendList {

    /**
     * 默认切割符
     */
    private static final String DEFAULT_SPLIT = " ";


    private static final CQCodeUtil CQ_CODE_UTIL = CQCodeUtil.build();

    /**
     * 内部拼接用的list类
     */
    private List<CharSequence> list;

    /**
     * 最终输出的时候的字符串拼接类, 每一次的输出都会刷新此对象
     */
    private volatile StringJoiner joiner;

    private final Supplier<StringJoiner> joinerGetter;

    /**
     * 无参构造，默认拼接符为一个空字符
     */
    public CQAppendList() {
        joinerGetter = () -> new StringJoiner(DEFAULT_SPLIT);
        updateJoiner();
        list = new ArrayList<>();
    }

    /**
     * 指定拼接符的构造
     */
    public CQAppendList(CharSequence delimiter) {
        joinerGetter = () -> new StringJoiner(delimiter);
        updateJoiner();
        list = new ArrayList<>();
    }

    /** 将joiner更新 */
    private synchronized void updateJoiner() {
        this.joiner = joinerGetter.get();
    }


    /**
     * 假如是一个CQCode或者CQCode字符串，转化为CQCode
     *
     * @param charSequence {@link CharSequence} 实现类
     * @return
     */
    private CharSequence ifCQCode(CharSequence charSequence) {
        //尝试转化为CQCode对象，如果无法转化则直接原样返回
        try {
            return CQ_CODE_UTIL.toCQCode(charSequence.toString());
        } catch (CQParseException e) {
            return charSequence;
        }
    }

    @Override
    public AppendList append(long append) {
        list.add(Long.toString(append));
        return this;
    }

    @Override
    public AppendList append(int append) {
        list.add(Integer.toString(append));
        return this;
    }

    @Override
    public AppendList append(double append) {
        list.add(Double.toString(append));
        return this;
    }

    @Override
    public AppendList append(boolean append) {
        list.add(Boolean.toString(append));
        return this;
    }

    @Override
    public AppendList append(char append) {
        list.add(Character.toString(append));
        return this;
    }

    @Override
    public AppendList append(CharSequence append) {
        list.add(ifCQCode(append));
        return this;
    }

    @Override
    public AppendList appendTrim(CharSequence append) {
        append(append.toString().trim());
        return this;
    }

    @Override
    public AppendList append(AppendList append) {
        append.forEach(this::append);
        return this;
    }

    @Override
    public AppendList appendTrim(AppendList append) {
        append.forEach(this::appendTrim);
        return this;
    }


    @Override
    public CQCode[] getCQCodes() {
        return list.stream().filter(c -> c instanceof CQCode).toArray(CQCode[]::new);
    }

    @Override
    public CharSequence get(int index) {
        return list.get(index);
    }

    @Override
    public CharSequence[] getAll() {
        return list.toArray(new CharSequence[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean containsType(CQCodeTypes types) {
        return list.stream().filter(c -> c instanceof CQCode).anyMatch(c -> ((CQCode) c).getCQCodeTypes().equals(types));
    }


    @Override
    public void forEach(Consumer<CharSequence> each) {
        list.forEach(each);
    }


    @Override
    public void forEachCQCode(BiConsumer<CQCode, Integer> each) {
        for (int i = 0; i < list.size(); i++) {
            CharSequence item = list.get(i);
            if (item instanceof CQCode) {
                each.accept((CQCode) item, i);
            }
        }
    }


    @Override
    public Stream<CharSequence> stream() {
        return list.stream();
    }

    @Override
    public Stream<CQCode> streamCQCode() {
        return list.stream().filter(c -> c instanceof CQCode).map(c -> (CQCode) c);
    }

    /**
     * 转为拼接字符串
     */
    @Override
    public synchronized String toString() {
        list.forEach(joiner::add);
        String result = joiner.toString();
        updateJoiner();
        return result;
    }



}
