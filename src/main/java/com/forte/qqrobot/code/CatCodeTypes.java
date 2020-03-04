package com.forte.qqrobot.code;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre> CodeType, 用来指定CatCode的Code类型。
 * <pre> 不再使用枚举，而是静态注册的形式，并模仿枚举的格式。
 * <pre> 使用的时候请不要自行创建实例，而是使用静态方法进行注册，并通过注册名称来获取，就像枚举那样子。否则在equals的判断下可能会出现错误。
 * <pre> 此类用来取代枚举类型，并作为一个可注册的假枚举类型使用。
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class CatCodeTypes implements Comparable<CatCodeTypes> {
    public static final String HEAD = "[CAT:";
    public static final String END = "]";
    public static final String SPLIT = ",";
    private static final AtomicInteger INDEX = new AtomicInteger(1);
    private static final Map<String, CatCodeTypes> TYPES = new ConcurrentHashMap<>(16);


    //**************** 枚举常量 ****************//
    /**
     * [CAT:face,id={1}] - QQ表情
     * {1} 为≥0的数字
     * 举例：[CAT:emoji,id=128513]（发送一个大笑的emoji表情）
     */
    public static final CatCodeTypes face = register("face", "face", "id");

    /**
     * [CAT:bface,id={1}] - 原创表情
     * {1}为该原创表情的ID，存放在酷Q目录的data\bface\下
     */
    public static final CatCodeTypes bface = register("bface", "bface", "p", "id");

    /**
     * [CAT:sface,id={1}] - 小表情
     * {1}为该小表情的ID
     */
    public static final CatCodeTypes sface = register("sface", "sface", "id");

    /**
     * [CAT:image,file={1}] - 发送自定义图片
     * {1}为图片文件名称，图片存放在酷Q目录的data\image\下
     * 举例：[CAT:image,file=1.jpg]
     * <p>
     * 部分插件也支持网络类型或者本地文件类型，所以添加路径格式
     */
    public static final CatCodeTypes image = register("image", "image", "file");

    /**
     * 语音
     * [CAT:record,file={1},magic={2}] - 发送语音
     * {1}为音频文件名称
     * {2}为是否为变声，若该参数为true则显示变声标记。该参数可被忽略。
     * 举例：[CAT:record,file=1.silk，magic=true]
     */
    public static final CatCodeTypes record = register("record", "record", "file", "magic");

    /**
     * [CAT:at,qq={1}] - @某人
     * {1}为被@的群成员账号。若该参数为all，则@全体成员（次数用尽或权限不足则会转换为文本）。
     * 举例：[CAT:at,qq=123456]
     */
    public static final CatCodeTypes at = register("at", "at", "qq");

    /**
     * 猜拳魔法
     * [CAT:rps,type={1}] - 发送猜拳魔法表情
     * {1}为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * 1 - 猜拳结果为石头
     * 2 - 猜拳结果为剪刀
     * 3 - 猜拳结果为布
     */
    public static final CatCodeTypes rps = register("rps", "rps", "type");

    /**
     * [CAT:dice,type={1}] - 发送掷骰子魔法表情
     * {1}对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     */
    public static final CatCodeTypes dice = register("dice", "dice", "type");

    /**
     * [CAT:shake] - 戳一戳（原窗口抖动，仅支持好友消息使用）
     */
    public static final CatCodeTypes shake = register("shake", "shake");

    /**
     * [CAT:anonymous,ignore={1}] - 匿名发消息（仅支持群消息使用）
     * 本CAT码需加在消息的开头。
     * 当{1}为true时，代表不强制使用匿名，如果匿名失败将转为普通消息发送。
     * 当{1}为false或ignore参数被忽略时，代表强制使用匿名，如果匿名失败将取消该消息的发送。
     * 举例：
     * [CAT:anonymous,ignore=true]
     * [CAT:anonymous]
     */
    public static final CatCodeTypes anonymous = register("anonymous", "anonymous", "ignore");

    /**
     * 音乐
     * [CAT:music,type={1},id={2},style={3}]
     * {1} 音乐平台类型，目前支持qq、163
     * {2} 对应音乐平台的数字音乐id
     * {3} 音乐卡片的风格。该参数可被忽略。
     * 注意：音乐只能作为单独的一条消息发送
     * 例子
     * [CAT:music,type=qq,id=422594]（发送一首QQ音乐的“Time after time”歌曲到群内）
     * [CAT:music,type=163,id=28406557]（发送一首网易云音乐的“桜咲く”歌曲到群内）
     */
    public static final CatCodeTypes music = register("music", "music", "type", "id", "style");

    /**
     * [CAT:music,type=custom,url={1},audio={2},title={3},content={4},image={5}] - 发送音乐自定义分享
     * {1}为分享链接，即点击分享后进入的音乐页面（如歌曲介绍页）。
     * {2}为音频链接（如mp3链接）。
     * {3}为音乐的标题，建议12字以内。
     * {4}为音乐的简介，建议30字以内。该参数可被忽略。
     * {5}为音乐的封面图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：音乐自定义分享只能作为单独的一条消息发送
     */
    public static final CatCodeTypes music_custom = register("music_custom", "music", "type", "url", "audio", "title", "content", "image");

    /**
     * [CAT:share,url={1},title={2},content={3},image={4}] - 发送链接分享
     * {1}为分享链接。
     * {2}为分享的标题，建议12字以内。
     * {3}为分享的简介，建议30字以内。该参数可被忽略。
     * {4}为分享的图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：链接分享只能作为单独的一条消息发送
     */
    public static final CatCodeTypes share = register("share", "share", "url", "title", "content", "image");

    /**
     * emoji表情
     */
    public static final CatCodeTypes emoji = register("emoji", "emoji", "id");

    /**
     * 地点
     * [CAT:location,lat={1},lon={2},title={3},content={4}]
     * {1} 纬度
     * {2} 经度
     * {3} 分享地点的名称
     * {4} 分享地点的具体地址
     */
    public static final CatCodeTypes location = register("location", "location", "lat", "lon", "title", "content");

    /**
     * 签到
     * [CAT:sign,location={1},title={2},image={3}]
     * 该CQ码仅支持接收。
     * {1} 用户签到的地点，为中文字串
     * {2} 用户签到时发表的心情文字
     * {3} 签到卡片所使用的背景图片连接
     */
    public static final CatCodeTypes sign = register("sign", "sign", "location", "title", "image");

    /**
     * 厘米秀一类的东西
     * content, qq都可以被忽略
     */
    public static final CatCodeTypes show = register("show", "show", "content", "id", "qq");

    /**
     * 联系人分享，type一般可能是qq或者group
     * [CAT:contact,id=111111111,type=qq]
     */
    public static final CatCodeTypes contact = register("contact", "contact", "id", "type");

    /**
     * 其他富文本消息
     */
    public static final CatCodeTypes rich = register("rich", "rich", "content", "text", "title");




    //**************** 字段参数 ****************//

    /**
     * 类型名称，例如"at"
     */
    private final String typeName;

    /**
     * 注册名称
     */
    private final String registerName;

    /**
     * 可能存在的参数列表, 一般仅用来做参考
     */
    private final String[] params;

    /**
     * 排序索引
     */
    private final int sort;

    /**
     * 私有构造
     */
    private CatCodeTypes(String name, String type, int sort, String... params) {
        this.registerName = name;
        this.typeName = type;
        this.sort = sort;
        this.params = Arrays.copyOf(params, params.length);
    }

    /**
     * 注册一个新的CatType，如果此类型的name存在，则抛出异常
     * @param name    类型的name, 相当于枚举的name
     * @param type    类型名称
     * @param params  可能存在的params
     * @return
     */
    public static CatCodeTypes register(String name, String type, String... params){
        CatCodeTypes cat = new CatCodeTypes(name, type, INDEX.getAndAdd(1), params);
        return TYPES.merge(name, cat, (old, val) -> {
            throw new IllegalArgumentException("type '"+ name +"' has already exists.");
        });
    }

    /**
     * 注册一个新的CatType，如果此类型的name存在，则抛出异常
     * @param name    类型的name, 相当于枚举的name
     * @param type    类型名称
     * @param params  可能存在的params
     * @return
     */
    public static CatCodeTypes register(String name, String type, int sort, String... params){
        synchronized (INDEX) {
            int index = INDEX.get();
            if(index == sort){
                // 如果排序值相同，index加一
                INDEX.addAndGet(1);
            }else{
                // 如果不同，如果当前排序值比参数小，则设置为当前排序值+1
                if(index < sort){
                    INDEX.set(sort);
                }
                // 如果大，不变
            }
        }
        CatCodeTypes cat = new CatCodeTypes(name, type, sort, params);
        return TYPES.merge(name, cat, (old, val) -> {
            throw new IllegalArgumentException("type '"+ name +"' has already exists.");
        });
    }

    /**
     * 根据注册名称获取type
     * @param name 注册名称
     */
    public static CatCodeTypes valueOf(String name){
        return TYPES.get(name);
    }

    /**
     * 获取全部类型
     */
    public static CatCodeTypes[] values(){
        return TYPES.values().stream().sorted().toArray(CatCodeTypes[]::new);
    }

    //**************** 部分API ****************//

    public int getSort(){
        return sort;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getName(){
        return registerName;
    }

    public String[] getParams(){
        return Arrays.copyOf(params, params.length);
    }

    public String getParams(int index){
        return params[index];
    }

    public int getParamLength(){
        return params.length;
    }

    @Override
    public String toString(){
        return getName();
    }

    /**
     * <pre> 判断两个类型是否相同。
     * <pre> 保证唯一性的前提下，直接进行内存比较判断。
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }


    /**
     * 排序接口的实现
     */
    @Override
    public int compareTo(CatCodeTypes o) {
        return Integer.compare(sort, o.sort);
    }
}
