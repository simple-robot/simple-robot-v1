package com.forte.qqrobot.beans.types;

import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * CQ码的全部function名
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:55
 * @since JDK1.8
 **/
public enum CQCodeTypes {

    /** 默认的未知类型，当无法获取或解析的时候将会使用此类型 */
    defaultType("", new String[0], new String[0], new String[0]),
    /**
     *  [CQ:face,id={1}] - QQ表情
     *  {1}为emoji字符的unicode编号
     * 举例：[CQ:emoji,id=128513]（发送一个大笑的emoji表情）
     */
    face("face",
            new String[]{"id"},
            new String[0],
            new String[]{"\\d+"}),

    /**
     * [CQ:bface,id={1}] - 原创表情
     * {1}为该原创表情的ID，存放在酷Q目录的data\bface\下
     */
    bface("bface",
            new String[]{"id"},
            new String[0],
            new String[]{"\\w+"}),

    /**
     * [CQ:sface,id={1}] - 小表情
     * {1}为该小表情的ID
     */
    sface("sface",
            new String[]{"id"},
            new String[0],
            new String[]{"\\d+"}),

    /**
     * [CQ:image,file={1}] - 发送自定义图片
     * {1}为图片文件名称，图片存放在酷Q目录的data\image\下
     * 举例：[CQ:image,file=1.jpg]（发送data\image\1.jpg）
     */
    image("image",
            new String[]{"file"},
            new String[0],
            new String[]{"[\\w\\.]+"}),


    /**
     * [CQ:record,file={1},magic={2}] - 发送语音
     * {1}为音频文件名称，音频存放在酷Q目录的data\record\下
     * {2}为是否为变声，若该参数为true则显示变声标记。该参数可被忽略。
     * 举例：[CQ:record,file=1.silk，magic=true]（发送data\record\1.silk，并标记为变声）
     */
    record("record",
            new String[]{"file", "magic"},
            new String[]{"magic"},
            new String[]{"[\\w\\.]+" , "(true|TRUE|false|FALSE)"}),

    /**
     * [CQ:at,qq={1}] - @某人
     * {1}为被@的群成员QQ。若该参数为all，则@全体成员（次数用尽或权限不足则会转换为文本）。
     * 举例：[CQ:at,qq=123456]
     */
    at("at",
            new String[]{"qq"},
            new String[0],
            new String[]{"(\\d+|all)"}),

    /**
     * [CQ:rps,type={1}] - 发送猜拳魔法表情
     * {1}为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * 1 - 猜拳结果为石头
     * 2 - 猜拳结果为剪刀
     * 3 - 猜拳结果为布
     */
    rps("rps",
            new String[]{"type"},
            new String[]{"type"},
            new String[]{"[1-3]"}),

    /**
     * [CQ:dice,type={1}] - 发送掷骰子魔法表情
     * {1}对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     */
    dice("dice",
            new String[]{"type"},
            new String[]{"type"},
            new String[]{"[1-6]"}),

    /**
     * [CQ:shake] - 戳一戳（原窗口抖动，仅支持好友消息使用）
     */
    shake("shake",
            new String[]{},
            new String[]{},
            new String[]{}),

    /**
     * [CQ:anonymous,ignore={1}] - 匿名发消息（仅支持群消息使用）
     * 本CQ码需加在消息的开头。
     * 当{1}为true时，代表不强制使用匿名，如果匿名失败将转为普通消息发送。
     * 当{1}为false或ignore参数被忽略时，代表强制使用匿名，如果匿名失败将取消该消息的发送。
     * 举例：
     * [CQ:anonymous,ignore=true]
     * [CQ:anonymous]
     */
    anonymous("anonymous",
            new String[]{"ignore"},
            new String[]{"ignore"},
            new String[]{"(true|TRUE|false|FALSE)"}),

    /**
     * [CQ:music,type={1},id={2}] - 发送音乐
     * {1}为音乐平台类型，目前支持qq、163、xiami
     * {2}为对应音乐平台的数字音乐id
     * 注意：音乐只能作为单独的一条消息发送
     * 举例：
     * [CQ:music,type=qq,id=422594]（发送一首QQ音乐的“Time after time”歌曲到群内）
     * [CQ:music,type=163,id=28406557]（发送一首网易云音乐的“桜咲く”歌曲到群内）
     */
    music("music",
            new String[]{"type", "id"},
            new String[0],
            new String[]{"(qq|163|xiami)" , "\\d+"}),

    /**
     * [CQ:music,type=custom,url={1},audio={2},title={3},content={4},image={5}] - 发送音乐自定义分享
     * {1}为分享链接，即点击分享后进入的音乐页面（如歌曲介绍页）。
     * {2}为音频链接（如mp3链接）。
     * {3}为音乐的标题，建议12字以内。
     * {4}为音乐的简介，建议30字以内。该参数可被忽略。
     * {5}为音乐的封面图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：音乐自定义分享只能作为单独的一条消息发送
     */
    music_custom("music",
            new String[]{"type", "url", "audio", "title", "content", "image"},
            new String[]{"content", "image"},
            new String[]{"custom" , "[\\w:\\\\/\\?=\\.]+" , "[\\w:\\\\/\\?=\\.]+" , ".+" , ".+",  "[\\w:\\\\/\\?=\\.]*"}),

    /**
     * [CQ:share,url={1},title={2},content={3},image={4}] - 发送链接分享
     * {1}为分享链接。
     * {2}为分享的标题，建议12字以内。
     * {3}为分享的简介，建议30字以内。该参数可被忽略。
     * {4}为分享的图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：链接分享只能作为单独的一条消息发送
     */
    share("share",
            new String[]{"url", "title", "content", "image"},
            new String[]{"content", "image"},
            new String[]{"[\\w:\\\\/\\?=\\.]+", ".+" , ".+" , "[\\w:\\\\/\\?=\\.]*"}),

    /**
     * emoji表情
     */
    emoji("emoji",
            new String[]{"id"},
            new String[0],
            new String[]{"\\d+"}),



    ;


    /**
     * 根据function名(即[CQ:后面的名字)获取CQCodeTypes对象实例
     * @param function 名称
     * @return CQCodeTypes实例对象
     */
    public static CQCodeTypes getTypeByFunction(String function){
        for (CQCodeTypes type : values()) {
            if(type.function.equals(function)){
                return type;
            }
        }
        return defaultType;
    }


    //**************** 普通常量 ****************//


    /** 类型名 */
    private final String function;
    /** 参数列表 */
    private final String[] keys;
    /** 按照索引对应着每个参数的数据类型匹配规则，正则 */
    private final String[] valuesRegex;
    /** 可以被忽略的参数列表 */
    private final Set<String> ignoreAbleKeys;
    /** 对此CQ码进行匹配的正则表达式 */
    private final String matchRegex;

    //**************** 静态常量 ****************//

    /** CQ码匹配的开头，从function开始拼接 */
    private static final String CQ_REGEX_HEAD = "\\[CQ:((?!(\\[CQ:))";
    /** CQ码匹配的结尾，在最后一个参数后 */
    private static final String CQ_REGEX_END = ")\\]";
    /** 用于从字符串中提取CQCode码字符串的正则表达式 */
//    private static final String CQCODE_EXTRACT_REGEX = "\\[CQ:((?!(\\[CQ:)).)+\\]";
    private static final String CQCODE_EXTRACT_REGEX = "\\[CQ:((?!(\\[CQ:))\\w)+\\,((?!(\\[CQ:)).)+\\]";

    /** 获取方法类型名称 */
    public String getFunction(){
        return function;
    }

    /** 获取参数列表 */
    public String[] getKeys(){
        return keys;
    }

    /** 获取可以忽略的key集合 */
    public Set<String> getIgnoreAbleKeys(){
        return ignoreAbleKeys;
    }

    /** 获取CQ码全匹配正则 */
    public static String getCqcodeExtractRegex(){
        return CQCODE_EXTRACT_REGEX;
    }

    /** 获取某个指定的key的匹配规则 */
    public String getKeyRegex(String key){
        if(key == null) return null;

        //遍历key
        for (int i = 0; i < this.keys.length; i++) {
            if(this.keys[i].equals(key)){
                return this.valuesRegex[i];
            }
        }
        //找不到，返回null
        return null;
    }

    /** 获取匹配字符串 */
    public String getMatchRegex(){
        return this.matchRegex;
    }

    /** 查看某个字符串是否为此类型的CQ码 */
    public boolean match(String text){
        return text.matches(this.matchRegex);
    }

    /**
     * 查看某个字符串中是否存在此类型的CQ码
     */
    public boolean contains(String text){
        return text.contains(this.matchRegex);
    }

    /**
     * 构造方法
     * @param function          cq码类型
     * @param keys              参数列表
     * @param ignoreAbleKeys    可以忽略的参数列表
     * @param valuesRegex       对应参数的参数值匹配正则
     */
    CQCodeTypes(String function, String[] keys, String[] ignoreAbleKeys , String[] valuesRegex){
        this.function = function;
        this.keys = keys;
        this.ignoreAbleKeys = Arrays.stream(ignoreAbleKeys).collect(Collectors.toSet());
        this.valuesRegex = valuesRegex;

        //生成匹配正则表达式
        StringJoiner joiner = new StringJoiner("" , CQ_REGEX_HEAD + this.function , CQ_REGEX_END);
        //遍历keys
        for (int i = 0; i < keys.length; i++) {
            String key = this.keys[i];
            String regex = this.valuesRegex[i];
            boolean ignoreAble = this.ignoreAbleKeys.contains(key);
            //如果可忽略，使用括号括住并在最后加上?
            String in = "," + key + "=" + regex;
            if(ignoreAble)
                in = ("(" + in + ")?");

            joiner.add(in);
        }

        //遍历完成，生成字符串
        this.matchRegex = joiner.toString();

    }

//    /**
////     * 获取本类全部常量对象
////     */
//    private static CQCodeTypes[] getAllTypes(){
//        return values();
//    }


}
