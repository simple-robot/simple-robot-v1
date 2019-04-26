package com.forte.qqrobot.listener.invoker.plug;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.exception.NoSuchBlockNameException;
import com.forte.qqrobot.listener.invoker.ListenerMethod;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 阻断器接口，
 * 并根据此接口定义一个当{@link com.forte.qqrobot.listener.invoker.ListenerManager}为空的时候所使用的空阻断类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Plug {

    /**
     * 将某名称的阻断函数进行阻断
     *
     * @param blockName 阻断名称
     * @param append    是否追加，如果为true，则如果当前已经存在阻断函数，将会追加；<br>
     *                  如果为false，则如果当前已经存在阻断函数，将会顶替；
     */
    void onBlock(String blockName, boolean append);

    /**
     * 将某个监听方法根据他的阻断名称获取全部阻断并添加至阻断容器
     * @param listenerMethod    监听函数
     * @param append            是否追加
     */
    void onBlockByName(ListenerMethod listenerMethod, boolean append);

    /**
     * 将某个监听方法添加至阻断函数，无视名称，仅添加其一个
     * @param listenerMethod    监听函数
     * @param append            是否叠加
     */
    void onBlockByMethod(ListenerMethod listenerMethod, boolean append);

    /**
     * 取消当前阻断
     */
    void unBlock();

    /**
     * 全局阻塞一个监听函数
     */
    void onGlobalBlock(String name);

    /**
     * 根据此监听函数的某指定索引的名称来添加全局阻塞
     */
    void onGlobalBlock(ListenerMethod listenerMethod, int blockNameIndex) throws NoSuchBlockNameException;

    /**
     * 使用此监听器的第一个名称来启动全局阻塞
     */
    void onGlobalBlock(ListenerMethod listenerMethod);

    /**
     * 取消全局阻塞
     */
    void unGlobalBlock();


//**************************************
    //*             阻断状态判断
    //**************************************


    /**
     * 判断是否存在阻塞函数
     * @return 是否存在阻塞函数
     */
    boolean hasBlock();

    /**
     * 判断是否存在全局阻塞函数
     * @return 是否存在全局阻塞函数
     */
    boolean hasGlobalBlock();

    /**
     * 判断是否存在普通阻塞
     * @return  是否存在全局阻塞函数
     */
    boolean hasNormalBlock();


    //**************************************
    //*             阻断信息判断
    //**************************************

    /**
     * 获取当前在全局阻塞状态的阻断名
     * @return
     */
    String getGlobalBlockName();

    /**
     * 获取当前处于阻断状态的阻断名
     * @return
     */
    String[] getNormalBlockNameArray();

    /**
     * 判断某个分组下的阻断是否处于阻断状态
     */
    boolean isOnGlobalBlock(String blockName);

    /**
     * 判断记录的开启阻断的列表中是否存在此名称
     * @param name 阻断组名
     * @return  是否存在
     */
    boolean isOnNormalBlock(String name);

    /**
     * 判断此监听函数的所有所在组是否都存在与阻断队列中
     * @return 所有所在组是否都存在与阻断队列中
     */
    boolean isAllOnNormalBlockByName(ListenerMethod listenerMethod);

    /**
     * 判断此监听函数的所在组是否有任意存在于阻断队列中
     * @return 是否有任意存在于阻断队列中
     */
    boolean isAnyOnNormalBlockByName(ListenerMethod listenerMethod);

    /**
     * 判断此监听函数的所在组是否有没有任何存在于阻断队列中
     * @return 是否有没有任何存在于阻断队列中
     */
    boolean isNoneOnNormalBlockByName(ListenerMethod listenerMethod);

    /**
     * 根据组名或默认名称判断
     */
    boolean isOnNormalBlockByThis(ListenerMethod listenerMethod);

    /**
     * 仅根据默认名称判断
     */
    boolean osOnNormalBlockByOnlyThis(ListenerMethod listenerMethod);


    //**************************************
    //*             阻断函数获取
    //**************************************


    /**
     * 获取当前生效的阻塞函数（如果存在的话）<br>
     * 获取顺序 全局 -> 如果为null -> 普通 -> 如果为null -> null <br>
     * global -> if null -> normal -> if null -> null
     * @return 当前生效的阻塞函数集合
     */
    Map<MsgGetTypes, Set<ListenerMethod>> getBlockMethod();

    /**
     * 根据消息分类获取当前生效的阻塞函数（如果存在的话）<br>
     * 获取顺序 全局 -> 如果为null -> 普通 -> 如果为null -> null <br>
     * global -> if null -> normal -> if null -> null
     * @param type
     * @return
     */
    Set<ListenerMethod> getBlockMethod(MsgGetTypes type);

    /**
     * 获取当前的全局阻塞函数
     * @return 当前的全局阻塞函数
     */
    Map<MsgGetTypes, Set<ListenerMethod>> getGlobalBlockMethod();

    /**
     * 根据消息分类获取当前的全局阻塞函数
     * @return 当前的全局阻塞函数
     */
    Set<ListenerMethod> getGlobalBlockMethod(MsgGetTypes type);

    /**
     * 获取当前的普通阻塞函数
     * @return 当前的普通阻塞函数
     */
    Map<MsgGetTypes, Set<ListenerMethod>> getNormalBlockMethod();

    /**
     * 根据分类获取当前的普通阻塞函数
     * @return 当前的普通阻塞函数
     */
    Set<ListenerMethod> getNormalBlockMethod(MsgGetTypes type);


    /**
     * 提供一个空的Plug类
     */
    class EmptyPlug implements Plug{
        /** 唯一静态常量 */
        private static final EmptyPlug SINGLE = new EmptyPlug();
        /** 唯一工厂 */
        public static EmptyPlug build(){
            return SINGLE;
        }
        @Override
        public void onBlock(String blockName, boolean append) {
        }
        @Override
        public void onBlockByName(ListenerMethod listenerMethod, boolean append) {
        }
        @Override
        public void onBlockByMethod(ListenerMethod listenerMethod, boolean append) {
        }
        @Override
        public void unBlock() {
        }
        @Override
        public void onGlobalBlock(String name) {
        }
        @Override
        public void onGlobalBlock(ListenerMethod listenerMethod, int blockNameIndex) throws NoSuchBlockNameException {
        }
        @Override
        public void onGlobalBlock(ListenerMethod listenerMethod) {
        }
        @Override
        public void unGlobalBlock() {
        }
        @Override
        public boolean hasBlock() {
            return false;
        }
        @Override
        public boolean hasGlobalBlock() {
            return false;
        }
        @Override
        public boolean hasNormalBlock() {
            return false;
        }
        @Override
        public String getGlobalBlockName() {
            return null;
        }
        @Override
        public String[] getNormalBlockNameArray() {
            return new String[0];
        }
        @Override
        public boolean isOnGlobalBlock(String blockName) {
            return false;
        }
        @Override
        public boolean isOnNormalBlock(String name) {
            return false;
        }
        @Override
        public boolean isAllOnNormalBlockByName(ListenerMethod listenerMethod) {
            return false;
        }
        @Override
        public boolean isAnyOnNormalBlockByName(ListenerMethod listenerMethod) {
            return false;
        }
        @Override
        public boolean isNoneOnNormalBlockByName(ListenerMethod listenerMethod) {
            return false;
        }
        @Override
        public boolean isOnNormalBlockByThis(ListenerMethod listenerMethod) {
            return false;
        }
        @Override
        public boolean osOnNormalBlockByOnlyThis(ListenerMethod listenerMethod) {
            return false;
        }
        @Override
        public Map<MsgGetTypes, Set<ListenerMethod>> getBlockMethod() {
            return null;
        }
        @Override
        public Set<ListenerMethod> getBlockMethod(MsgGetTypes type) {
            return null;
        }
        @Override
        public Map<MsgGetTypes, Set<ListenerMethod>> getGlobalBlockMethod() {
            return null;
        }
        @Override
        public Set<ListenerMethod> getGlobalBlockMethod(MsgGetTypes type) {
            return null;
        }
        @Override
        public Map<MsgGetTypes, Set<ListenerMethod>> getNormalBlockMethod() {
            return null;
        }
        @Override
        public Set<ListenerMethod> getNormalBlockMethod(MsgGetTypes type) {
            return null;
        }
        private EmptyPlug(){}
    }


}
