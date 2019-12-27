package com.forte.qqrobot.listener.invoker.plug;

import com.forte.qqrobot.anno.Block;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.exception.NoSuchBlockNameException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;
import com.forte.utils.collections.Maputer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * 监听器阻塞器
 * 每种种类的监听器同一时间仅只能有一个阻塞中的监听器，且阻塞的监听器只能由自己解除阻塞（或定时解除）
 * 阻塞后，同类型的监听器不论是普通监听器还是备用监听器都将失去作用，无法接收到消息，直到解除阻塞
 * 同时过滤器注解也将失去作用，并且提供为阻塞的时候专用的阻塞过滤器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/15 15:45
 * @since JDK1.8
 **/
public class ListenerPlug implements Plug {

    /**
     * 全局阻塞容器, 仅只能根据名称阻塞，不可追加
     * Global block ListenerMethod, only by name, Can not append
     **/
    private final AtomicReference<Map<MsgGetTypes, Set<ListenerMethod>>> GLOBAL_BLOCK = new AtomicReference<>(null);

    /**
     * 处于全局阻断状态的阻断组名，在添加阻断的时候同步更新
     */
    private final AtomicReference<String> ON_GLOBAL_BLOCK_NAME = new AtomicReference<>(null);

    /**
     * 分类型的阻塞容器
     * 普通的阻塞容器，当没有全局阻塞的时候此阻塞容器生效
     */
    private final AtomicReference<Map<MsgGetTypes, Set<ListenerMethod>>> NORMAL_BLOCK = new AtomicReference<>(null);

    /**
     * 记录被添加至普通阻塞容器的组名<br>
     * 注意：使用单体增加的监听函数将会使用获取默认名称的方式来记录名称而不是组名
     *
     */
    private final Set<String> ON_NORMAL_BLOCK_NAMES = new CopyOnWriteArraySet<>();

    /**
     * 保存全部监听函数的集合，根据block的名称分组
     */
    private final Map<String, Map<MsgGetTypes, Set<ListenerMethod>>> ALL_LISTENERMETHODS;

    //**************************************
    //*             进入阻断的方法
    //**************************************


    /**
     * 将某名称的阻断函数进行阻断
     *
     * @param blockName 阻断名称
     * @param append    是否追加，如果为true，则如果当前已经存在阻断函数，将会追加；<br>
     *                  如果为false，则如果当前已经存在阻断函数，将会顶替；
     */
    @Override
    public void onBlock(String blockName, boolean append) {
        //更新函数
        UnaryOperator<Map<MsgGetTypes, Set<ListenerMethod>>> update;

        //如果为追加
        if (append) {
            update = old -> {
                //如果为空，直接返回名字下的分类结果
                if(old == null){
                    Map<MsgGetTypes, Set<ListenerMethod>> map = ALL_LISTENERMETHODS.get(blockName);
                    if(map != null){
                        //如果不为空，认为添加成功
                        saveNormalName(blockName, append);

                    }else{
                        //如果为null，将会被赋值为null，则清空记录
                        clearNormalName();
                    }
                    return map;
                }

                //向旧的里面追加
                old.forEach((k, v) -> {
                    //获取指定名字下的分类
                    Map<MsgGetTypes, Set<ListenerMethod>> map = ALL_LISTENERMETHODS.get(blockName);
                    if(map != null){
                        //如果有值，则认为添加成功，记录为成功
                        saveNormalName(blockName, append);

                        //获取分类下的全部监听函数
                        Set<ListenerMethod> set = map.get(k);
                        if(set != null && set.size() > 0){
                            //添加
                            v.addAll(set);
                        }
                    }
                });

                return old;
            };

        } else {
            //如果是不追加，直接替换
            Map<MsgGetTypes, Set<ListenerMethod>> map = ALL_LISTENERMETHODS.get(blockName);
            //如果不为null，则认为添加成功
            if(map != null){
                saveNormalName(blockName, append);
            }else{
                //如果为null，则会被赋值为null，清空名称记录
                clearNormalName();
            }
            update = old -> map;
        }

        //更新
        NORMAL_BLOCK.updateAndGet(update);

    }

    /**
     * 将某个监听方法根据他的阻断名称获取全部阻断并添加至阻断容器
     * @param listenerMethod    监听函数
     * @param append            是否追加
     */
    @Override
    public void onBlockByName(ListenerMethod listenerMethod, boolean append) {
        //将此名称下的全部加入阻断列表
        //如果不追加，清空当前阻断
        if (!append) {
            unBlock();
        }

        for (String name : getBlockNames(listenerMethod)) {
            onBlock(name, true);
        }

    }

    /**
     * 将某个监听方法添加至阻断函数，无视名称，仅添加其一个
     * @param listenerMethod    监听函数
     * @param append            是否叠加
     */
    @Override
    public void onBlockByMethod(ListenerMethod listenerMethod, boolean append){
        //类型
        MsgGetTypes[] types = listenerMethod.getTypes();

        //更新函数
        UnaryOperator<Map<MsgGetTypes, Set<ListenerMethod>>> update;
        //如果为追加，则追加，如果不追加则之恶极替换
        if(append){
            update = old -> {
                //遍历监听类型
                for (MsgGetTypes type : types) {
                    //当前类型如果存在，添加，如果不存在，创建set并保存进去
                    Maputer.peek(old, type, ex -> ex.add(listenerMethod), () -> {
                        Set<ListenerMethod> set = new HashSet<>();
                        set.add(listenerMethod);
                        return set;
                    });
                }

                return old;
            };
        }else{
            //如果不追加，直接替换
            update = old -> {
                Map<MsgGetTypes, Set<ListenerMethod>> map = new HashMap<>(types.length);
                //遍历
                for (MsgGetTypes type : types) {
                    Set<ListenerMethod> set = new HashSet<>();
                    set.add(listenerMethod);
                    map.put(type, set);
                }
                return map;
            };
        }

        //记录阻断名称
        saveNormalName(getDefaultName(listenerMethod), append);

        //更新
        NORMAL_BLOCK.updateAndGet(update);
    }

    /**
     * 取消当前阻断, 需要线程同步
     */
    @Override
    public void unBlock() {
        NORMAL_BLOCK.getAndSet(null);
        //移除阻断记录
        clearNormalName();
    }


    /**
     * 全局阻塞一个监听函数
     */
    @Override
    public void onGlobalBlock(String name){
        //更新
        GLOBAL_BLOCK.getAndSet(ALL_LISTENERMETHODS.get(name));
        //同时更新名称
        saveGlobalName(name);
    }

    /**
     * 根据此监听函数的某指定索引的名称来添加全局阻塞
     */
    @Override
    public void onGlobalBlock(ListenerMethod listenerMethod, int blockNameIndex) throws NoSuchBlockNameException {
        //获取名称
        try {
            String blockName = getBlockNames(listenerMethod)[blockNameIndex];
            onGlobalBlock(blockName);
        }catch (IndexOutOfBoundsException e){
            throw new NoSuchBlockNameException("超出索引！");
        }
    }

    /**
     * 使用此监听器的第一个名称来启动全局阻塞
     */
    @Override
    public void onGlobalBlock(ListenerMethod listenerMethod){
        //获取名称能够保证至少长度为1
        try {
            onGlobalBlock(listenerMethod, 0);
        } catch (NoSuchBlockNameException e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消全局阻塞
     */
    @Override
    public void unGlobalBlock(){
        //设置为null
        GLOBAL_BLOCK.getAndSet(null);
        //移除记录
        removeGlobalName();
    }

    //**************************************
    //*             记录相关
    //**************************************

    /**
     * 刷新全局阻塞的组名
     * @param name
     */
    private void saveGlobalName(String name){
        ON_GLOBAL_BLOCK_NAME.getAndSet(name);
    }

    /**
     * 清除全局阻塞的组名
     */
    private void removeGlobalName(){
        saveGlobalName(null);
    }

    /**
     * 新增一个普通阻塞的名称
     */
    private void addNormalName(String name){
        ON_NORMAL_BLOCK_NAMES.add(name);
    }

    /**
     * 清空普通阻塞的名称
     */
    private void clearNormalName(){
        ON_NORMAL_BLOCK_NAMES.clear();
    }

    /**
     * 更新普通阻塞的名称
     */
    private void updateNormalName(String name){
        //先清空，再保存
        clearNormalName();
        addNormalName(name);
    }

    /**
     * 根据是否追加记录一个阻断信息
     * @param name      阻断名
     * @param append    是否追加
     */
    private void saveNormalName(String name, boolean append){
        //记录阻断名称
        if(append){
            //如果追加，追加记录
            addNormalName(name);
        }else{
            //清空后记录
            updateNormalName(name);
        }
    }

    //**************************************
    //*             阻断状态判断
    //**************************************


    /**
     * 判断是否存在阻塞函数
     * @return 是否存在阻塞函数
     */
    @Override
    public boolean hasBlock(){
        return hasGlobalBlock() && hasGlobalBlock();
    }

    /**
     * 判断是否存在全局阻塞函数
     * @return 是否存在全局阻塞函数
     */
    @Override
    public boolean hasGlobalBlock(){
        return GLOBAL_BLOCK.get() != null;
    }

    /**
     * 判断是否存在普通阻塞
     * @return  是否存在全局阻塞函数
     */
    @Override
    public boolean hasNormalBlock(){
        return NORMAL_BLOCK.get() != null;
    }


    //**************************************
    //*             阻断信息判断
    //**************************************

    /**
     * 获取当前在全局阻塞状态的阻断名
     * @return
     */
    @Override
    public String getGlobalBlockName(){
        return ON_GLOBAL_BLOCK_NAME.get();
    }

    /**
     * 获取当前处于阻断状态的阻断名
     * @return
     */
    @Override
    public String[] getNormalBlockNameArray(){
        return ON_NORMAL_BLOCK_NAMES.toArray(new String[0]);
    }

    /**
     * 判断某个分组下的阻断是否处于阻断状态
     */
    @Override
    public boolean isOnGlobalBlock(String blockName){
        String onGlobal = ON_GLOBAL_BLOCK_NAME.get();
        return onGlobal != null && onGlobal.equals(blockName);
    }

    /**
     * 判断记录的开启阻断的列表中是否存在此名称
     * @param name 阻断组名
     * @return  是否存在
     */
    @Override
    public boolean isOnNormalBlock(String name){
        return ON_NORMAL_BLOCK_NAMES.contains(name);
    }

    /**
     * 判断此监听函数的所有所在组是否都存在与阻断队列中
     * @return 所有所在组是否都存在与阻断队列中
     */
    @Override
    public boolean isAllOnNormalBlockByName(ListenerMethod listenerMethod){
        //获取全部组名
        String[] blockNames = getBlockNames(listenerMethod);

        //判断是否所有的名称都存在于阻断队列
        return Arrays.stream(blockNames).allMatch(this::isOnNormalBlock);
    }

    /**
     * 判断此监听函数的所在组是否有任意存在于阻断队列中
     * @return 是否有任意存在于阻断队列中
     */
    @Override
    public boolean isAnyOnNormalBlockByName(ListenerMethod listenerMethod){
        //获取全部组名
        String[] blockNames = getBlockNames(listenerMethod);

        //判断
        return Arrays.stream(blockNames).anyMatch(this::isOnNormalBlock);
    }

    /**
     * 判断此监听函数的所在组是否有没有任何存在于阻断队列中
     * @return 是否有没有任何存在于阻断队列中
     */
    @Override
    public boolean isNoneOnNormalBlockByName(ListenerMethod listenerMethod){
        //获取全部组名
        String[] blockNames = getBlockNames(listenerMethod);

        //判断
        return Arrays.stream(blockNames).noneMatch(this::isOnNormalBlock);
    }

    /**
     * 根据组名或默认名称判断
     */
    @Override
    public boolean isOnNormalBlockByThis(ListenerMethod listenerMethod){
        //先根据组名，如果组名没有再判断默认名
        return isAnyOnNormalBlockByName(listenerMethod) || ON_NORMAL_BLOCK_NAMES.contains(getDefaultName(listenerMethod));
    }

    /**
     * 仅根据默认名称判断
     */
    @Override
    public boolean osOnNormalBlockByOnlyThis(ListenerMethod listenerMethod){
        return ON_NORMAL_BLOCK_NAMES.contains(getDefaultName(listenerMethod));
    }




    //**************************************
    //*             阻断函数获取
    //**************************************


    /**
     * 获取当前生效的阻塞函数（如果存在的话）<br>
     * 获取顺序 全局 -> 如果为null -> 普通 -> 如果为null -> null <br>
     * global -> if null -> normal -> if null -> null
     * @return 当前生效的阻塞函数集合
     */
    @Override
    public Map<MsgGetTypes, Set<ListenerMethod>> getBlockMethod(){
        return Optional.ofNullable(getGlobalBlockMethod()).orElseGet(this::getNormalBlockMethod);
    }

    /**
     * 根据消息分类获取当前生效的阻塞函数（如果存在的话）<br>
     * 获取顺序 全局 -> 如果为null -> 普通 -> 如果为null -> null <br>
     * global -> if null -> normal -> if null -> null
     * @param type
     * @return
     */
    @Override
    public Set<ListenerMethod> getBlockMethod(MsgGetTypes type){
        return Optional.ofNullable(getGlobalBlockMethod(type)).orElseGet(() -> getNormalBlockMethod(type));
    }

    /**
     * 获取当前的全局阻塞函数
     * @return 当前的全局阻塞函数
     */
    @Override
    public Map<MsgGetTypes, Set<ListenerMethod>> getGlobalBlockMethod(){
        return GLOBAL_BLOCK.get();
    }

    /**
     * 根据消息分类获取当前的全局阻塞函数
     * @return 当前的全局阻塞函数
     */
    @Override
    public Set<ListenerMethod> getGlobalBlockMethod(MsgGetTypes type){
        return Optional.ofNullable(GLOBAL_BLOCK.get()).map(b -> b.get(type)).orElse(null);
    }

    /**
     * 获取当前的普通阻塞函数
     * @return 当前的普通阻塞函数
     */
    @Override
    public Map<MsgGetTypes, Set<ListenerMethod>> getNormalBlockMethod(){
        return NORMAL_BLOCK.get();
    }

    /**
     * 根据分类获取当前的普通阻塞函数
     * @return 当前的普通阻塞函数
     */
    @Override
    public Set<ListenerMethod> getNormalBlockMethod(MsgGetTypes type){
        return Optional.ofNullable(NORMAL_BLOCK.get()).map(b -> b.get(type)).orElse(null);
    }


    /**
     * 为监听函数取一个默认名称
     * 命名规则：函数名+@+listenerMethod的UUID
     */
    private static String getDefaultName(ListenerMethod listenerMethod) {
        String mName = listenerMethod.getMethod().getName();
        return mName + "@" + listenerMethod.getUUID();
    }

    /**
     * 获取监听函数的阻断名，保证返回的数组长度至少为1
     * @param listenerMethod    监听函数
     * @return  阻断名数组
     */
    private static String[] getBlockNames(ListenerMethod listenerMethod){
        return Optional.ofNullable(listenerMethod.getBlock()).map(Block::value).filter(s -> s.length > 0).orElseGet(() -> new String[]{getDefaultName(listenerMethod)});
    }

    /**
     * 构造，接收全部的监听函数并进行分类
     */
    public ListenerPlug(Set<ListenerMethod> listenerMethodSet) {
        //接收到全部的监听函数，根据Block注解开始分组
        //第一层分组，根据名称数组分类
        Map<String[], Set<ListenerMethod>> firstMap = listenerMethodSet.stream()
                .collect(Collectors.groupingBy(
                        ListenerPlug::getBlockNames,
                        Collectors.toSet()
                ));


        //创建map保存结果，初始长度为firstMap的两倍
        Map<String, Map<MsgGetTypes, Set<ListenerMethod>>> finalMap = new HashMap<>(firstMap.size() * 2);

        //遍历firstMap
        firstMap.forEach((kArr, set) -> {
            //遍历名称数组
            Arrays.stream(kArr).forEach(name -> {
                //给当前的set，根据分类追加
                Map<MsgGetTypes[], Set<ListenerMethod>> collect = set.stream().collect(Collectors.groupingBy(ListenerMethod::getTypes, Collectors.toSet()));
                //如果name存在, 追加, 不存在, 创建并保存
                Maputer.peek(finalMap, name,
                        //如果存在
                        m -> {
                            //遍历这个set, 根据MsgGetTypes添加
                            //如果存在，遍历这个key，查看全部监听类型并添加
                            collect.forEach((k, v) -> Arrays.stream(k).forEach(msgT -> Maputer.peek(m, msgT, sv -> sv.addAll(v), () -> new HashSet<>(v))));
                        },
                        //如果不存在，创建一个新的保存
                        () -> {
                            //遍历collect，转化为msgType, Set
                            Map<MsgGetTypes, Set<ListenerMethod>> nullResult = new HashMap<>();
                            collect.forEach((k, v) -> Arrays.stream(k).forEach(msgT -> Maputer.peek(nullResult, msgT, sv -> sv.addAll(v), () -> new HashSet<>(v))));
                            return nullResult;
                        });

            });

        });

        //赋值保存
        this.ALL_LISTENERMETHODS = finalMap;
    }


}
