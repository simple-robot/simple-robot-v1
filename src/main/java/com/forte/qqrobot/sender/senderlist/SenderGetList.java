package com.forte.qqrobot.sender.senderlist;

import com.forte.qqrobot.beans.messages.CodesAble;
import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.GroupCodeAble;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.GroupFileUpload;
import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.types.CacheTypes;
import com.forte.qqrobot.sender.CacheGetterFactory;
import com.forte.qqrobot.sender.intercept.InterceptValue;

import java.time.LocalDateTime;

/**
 * get相关方法列表，继承get总方法
 * 1.2.1增加扩充方法
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SenderGetList extends SenderList {

    //**************************************
    //*             get相关接口
    //**************************************

    /** 如果存在一些可以自定义获取数量的接口，默认使用此数量 */
    int NUMBER = 99;
    /** 如果存在一些可以自定义是否使用缓存的接口，默认使用此选项 */
    boolean CACHE = true;

    /**
     * 取匿名成员信息
     * 一般是使用匿名标识来获取
     * @return 匿名成员信息
     */
    @InterceptValue
    AnonInfo getAnonInfo(String flag);


    /**
     * 获取权限信息
     * 一般不需要参数
     * @return 权限信息
     */
    @InterceptValue
    AuthInfo getAuthInfo();

    /**
     * 获取封禁成员列表
     * @param group 群号
     * @return 封禁列表
     */
    @InterceptValue
    BanList getBanList(String group);

    /**
     * 获取封禁成员列表
     * @param group 群号
     * @return 封禁列表
     */
    default BanList getBanList(GroupCodeAble group){
        return getBanList(group.getGroupCode());
    }

    /**
     * 获取群文件信息
     * @param flag 文件标识
     * @return 群文件信息
     */
    @InterceptValue
    FileInfo getFileInfo(String flag);

    /**
     * 获取群文件信息
     * @param file 文件
     * @return 群文件信息
     */
    default FileInfo getFileInfo(GroupFileUpload file){
        return getFileInfo(file.getId());
    }

    /**
     * 获取好友列表
     * @return 好友列表
     */
    @InterceptValue
    FriendList getFriendList();

    /**
     * 取群作业列表
     * @param group 群号
     * @return 群作业列表
     */
    default GroupHomeworkList getGroupHomeworkList(String group){
        return getGroupHomeworkList(group, NUMBER);
    }

    /**
     * 取群作业列表
     * @param group 群号携带者
     * @return 群作业列表
     */
    default GroupHomeworkList getGroupHomeworkList(GroupCodeAble group){
        return getGroupHomeworkList(group.getGroupCode());
    }

    /**
     * 取群作业列表
     * @param group 群号
     * @param number 获取数量
     * @return 群作业列表
     */
    @InterceptValue
    GroupHomeworkList getGroupHomeworkList(String group, int number);

    /**
     * 取群作业列表
     * @param group 群号携带者
     * @return 群作业列表
     */
    default GroupHomeworkList getGroupHomeworkList(GroupCodeAble group, int number){
        return getGroupHomeworkList(group.getGroupCode(), number);
    }

    /**
     * 取群信息 使用缓存
     * @param group 群号
     * @return 群信息
     */
    default GroupInfo getGroupInfo(String group){
        return getGroupInfo(group, CACHE);
    }

    /**
     * 取群信息 使用缓存
     * @param group 群号携带者
     * @return 群信息
     */
    default GroupInfo getGroupInfo(GroupCodeAble group){
        return getGroupInfo(group.getGroupCode());
    }

    /**
     * 取群信息
     * @param group 群号
     * @param cache 是否使用缓存
     * @return 群信息
     */
    @InterceptValue
    GroupInfo getGroupInfo(String group, boolean cache);

    /**
     * 取群信息
     * @param group 群号携带者
     * @param cache 是否使用缓存
     * @return 群信息
     */
    default GroupInfo getGroupInfo(GroupCodeAble group, boolean cache){
        return getGroupInfo(group.getGroupCode(), cache);
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @return  群链接
     */
    default GroupLinkList getGroupLinkList(String group){
        return getGroupLinkList(group, NUMBER);
    }


    /**
     * 取群链接列表
     * @param group 群号
     * @return  群链接
     */
    default GroupLinkList getGroupLinkList(GroupCodeAble group){
        return getGroupLinkList(group.getGroupCode());
    }

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 获取数量
     * @return  群链接
     */
    @InterceptValue
    GroupLinkList getGroupLinkList(String group, int number);

    /**
     * 取群链接列表
     * @param group 群号
     * @param number 获取数量
     * @return  群链接
     */
    default GroupLinkList getGroupLinkList(GroupCodeAble group, int number){
        return getGroupLinkList(group.getGroupCode(), number);
    }

    /**
     * 取群列表
     * @return 群列表
     */
    @InterceptValue
    GroupList getGroupList();

    /**
     * 取群成员信息 使用缓存
     * @param group 群号
     * @param QQ    QQ号
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(String group, String QQ){
        return getGroupMemberInfo(group, QQ, CACHE);
    }

    /**
     * 取群成员信息 使用缓存
     * @param group 群号
     * @param QQ    QQ号
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(GroupCodeAble group, QQCodeAble QQ){
        return getGroupMemberInfo(group.getGroupCode(), QQ.getQQCode());
    }

    /**
     * 取群成员信息 使用缓存
     * @param codes 群号和QQ号携带者
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(CodesAble codes){
        return getGroupMemberInfo(codes.getGroupCode(), codes.getQQCode());
    }

    /**
     * 取群成员信息
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否使用缓存
     * @return 群成员信息
     */
    @InterceptValue
    GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache);


    /**
     * 取群成员信息 使用缓存
     * @param group 群号
     * @param QQ    QQ号
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(GroupCodeAble group, QQCodeAble QQ, boolean cache){
        return getGroupMemberInfo(group.getGroupCode(), QQ.getQQCode(), cache);
    }

    /**
     * 取群成员信息 使用缓存
     * @param codes 群号和QQ号携带者
     * @return 群成员信息
     */
    default GroupMemberInfo getGroupMemberInfo(CodesAble codes, boolean cache){
        return getGroupMemberInfo(codes.getGroupCode(), codes.getQQCode(), cache);
    }

    /**
     * 取群成员列表
     * @param group 群号
     * @return  成员列表
     */
    @InterceptValue
    GroupMemberList getGroupMemberList(String group);

    /**
     * 取群成员列表
     * @param group 群号
     * @return  成员列表
     */
    default GroupMemberList getGroupMemberList(GroupCodeAble group){
        return getGroupMemberList(group.getGroupCode());
    }

    /**
     * 取群公告列表
     * @param group 群号
     * @return  群公告列表
     */
    default GroupNoteList getGroupNoteList(String group){
        return getGroupNoteList(group, NUMBER);
    }

    /**
     * 取群公告列表
     * @param group 群号
     * @return  群公告列表
     */
    default GroupNoteList getGroupNoteList(GroupCodeAble group){
        return getGroupNoteList(group.getGroupCode());
    }

    /**
     * 取群公告列表
     * @param group 群号
     * @param number 数量
     * @return  群公告列表
     */
    @InterceptValue
    GroupNoteList getGroupNoteList(String group, int number);

    /**
     * 取群公告列表
     * @param group 群号
     * @param number 数量
     * @return  群公告列表
     */
    default GroupNoteList getGroupNoteList(GroupCodeAble group, int number){
        return getGroupNoteList(group.getGroupCode(), number);
    }


    /**
     * 取置顶群公告
     * @param group 群号
     * @return  置顶群公告
     */
    @InterceptValue
    GroupTopNote getGroupTopNote(String group);

    /**
     * 取置顶群公告
     * @param group 群号
     * @return  置顶群公告
     */
    default GroupTopNote getGroupTopNote(GroupCodeAble group){
        return getGroupTopNote(group.getGroupCode());
    }

    /**
     * 获取图片信息
     * @param flag  图片文件名或标识
     * @return  图片信息
     */
    @InterceptValue
    ImageInfo getImageInfo(String flag);

    /**
     * 获取图片信息
     * @param flag  图片文件名或标识
     * @return  图片信息
     */
    default ImageInfo getImageInfo(FlagAble flag){
        return getImageInfo(flag.getFlag());
    }

    /**
     * 获取登录的QQ的信息
     * @return 登录QQ的信息
     */
    @InterceptValue
    LoginQQInfo getLoginQQInfo();

    /**
     * 获取群共享文件列表
     * @return 共享文件列表
     */
    @InterceptValue
    ShareList getShareList(String group);

    /**
     * 获取群共享文件列表
     * @param group 群号携带者
     * @return 共享文件列表
     */
    default ShareList getShareList(GroupCodeAble group){
        return getShareList(group.getGroupCode());
    }

    /**
     * 取陌生人信息 使用缓存
     * @param QQ 陌生人的QQ号
     * @return
     */
    default StrangerInfo getStrangerInfo(String QQ){
        return getStrangerInfo(QQ, CACHE);
    }

    /**
     * 取陌生人信息 使用缓存
     * @param QQ 陌生人的QQ号
     * @return
     */
    default StrangerInfo getStrangerInfo(QQCodeAble QQ){
        return getStrangerInfo(QQ.getQQCode());
    }

    /**
     * 取陌生人信息
     * @param QQ 陌生人的QQ号
     * @param cache 是否使用缓存
     * @return
     */
    @InterceptValue
    StrangerInfo getStrangerInfo(String QQ, boolean cache);

    /**
     * 取陌生人信息
     * @param QQ 陌生人的QQ号
     * @param cache 是否使用缓存
     * @return
     */
    default StrangerInfo getStrangerInfo(QQCodeAble QQ, boolean cache){
        return getStrangerInfo(QQ.getQQCode(), cache);
    }


    //**************** 以下为转化为缓存代理对象的方法 ****************//


    /**
     * 获取默认的缓存器，默认缓存器数据缓存1小时
     * @deprecated  目前缓存使用动态代理以及localThread进行缓存，会很影响效率，所以在此标记过时，以待优化。
     */
    @CacheGetterFactory.NoCache
    @Deprecated
    default SenderGetList cache(){
        return CacheGetterFactory.toCacheableGetter(this);
    }

    /**
     * 转化为缓存getter
     * @param time 缓存保存的秒时长
     * @deprecated  目前缓存使用动态代理以及localThread进行缓存，会很影响效率，所以在此标记过时，以待优化。
     */
    @CacheGetterFactory.NoCache
    @Deprecated
    default SenderGetList cache(long time){
        return CacheGetterFactory.toCacheableGetter(this, time);
    }

    /**
     * 转化为缓存getter
     * @param time          时长
     * @param cacheTypes    时间对应的增量类型
     * @deprecated  目前缓存使用动态代理以及localThread进行缓存，会很影响效率，所以在此标记过时，以待优化。
     */
    @CacheGetterFactory.NoCache
    @Deprecated
    default SenderGetList cache(long time, CacheTypes cacheTypes){
        return CacheGetterFactory.toCacheableGetter(this, time, cacheTypes);
    }

    /**
     * 指定过期时间
     * @param to    到某个指定的时间过期
     * @deprecated  目前缓存使用动态代理以及localThread进行缓存，会很影响效率，所以在此标记过时，以待优化。
     */
    @CacheGetterFactory.NoCache
    @Deprecated
    default SenderGetList cache(LocalDateTime to){
        return CacheGetterFactory.toCacheableGetter(this, to);
    }


    /**
     * 取消缓存状态
     * @deprecated  目前缓存使用动态代理以及localThread进行缓存，会很影响效率，所以在此标记过时，以待优化。
     */
    @CacheGetterFactory.NoCache
    @Deprecated
    default SenderGetList dontCache(){
        SenderGetList originalGetter = CacheGetterFactory.getOriginalGetter();
        //如果没有，则认为自己就是原本的。
        return originalGetter == null ? this : originalGetter;
    }


}
