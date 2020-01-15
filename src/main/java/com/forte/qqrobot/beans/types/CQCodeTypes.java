package com.forte.qqrobot.beans.types;

import com.forte.qqrobot.utils.EnumValues;
import com.forte.utils.regex.RegexUtil;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 此枚举保存全部的CQCode类型。<br>
 * 且提供了一个{@link com.forte.qqrobot.factory.CQCodeTypeFactory}来支持动态扩展此枚举。<br>
 * 请不要使用任何非工厂创建的形式（例如自己通过反射或者其他工具）创建此类的实例对象。此类中维护一个function映射，
 * 用于通过function值快速定位CQCodeType，并且提供了一个注册接口{@link #register(CQCodeTypes)} 来支持额外注册的实例对象，并对其进行验证。
 * 包括{@link com.forte.qqrobot.factory.CQCodeTypeFactory}中也提供了直接创建新枚举实例的相关接口，并提供参数验证。
 * 假如您使用了其他手段自己创建了一个额外的实例，可能会导致valueOf所取值与内部的function映射值不相符、参数冲突等一系列问题。
 * <br>
 * <br>
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:55
 * @since JDK1.8
 **/
public enum CQCodeTypes {

    /**
     * 默认的未知类型，当无法获取或解析的时候将会使用此类型
     */
    defaultType("", new String[0], new String[0], new String[0], -99),

    /**
     * [CQ:face,id={1}] - QQ表情
     * {1} 为≥0的数字
     * 举例：[CQ:emoji,id=128513]（发送一个大笑的emoji表情）
     */
    face("face",
            new String[]{"id"},
            new String[0],
            new String[]{"\\d+"},
            1),

    /**
     * [CQ:bface,id={1}] - 原创表情
     * {1}为该原创表情的ID，存放在酷Q目录的data\bface\下
     */
    bface("bface",
            new String[]{"id"},
            new String[0],
            new String[]{".+"},
            2),

    /**
     * [CQ:sface,id={1}] - 小表情
     * {1}为该小表情的ID
     */
    sface("sface",
            new String[]{"id"},
            new String[0],
            new String[]{".+"},
            3),

    /**
     * [CQ:image,file={1}] - 发送自定义图片
     * {1}为图片文件名称，图片存放在酷Q目录的data\image\下
     * 举例：[CQ:image,file=1.jpg]（发送data\image\1.jpg）
     * <p>
     * 部分插件也支持网络类型或者本地文件类型，所以添加路径格式
     */
    image("image",
            new String[]{"file"},
            new String[0],
            new String[]{".+"},
            4),


    /**
     * 语音
     * [CQ:record,file={1},magic={2}] - 发送语音
     * {1}为音频文件名称，音频存放在酷Q目录的data\record\下
     * {2}为是否为变声，若该参数为true则显示变声标记。该参数可被忽略。
     * 举例：[CQ:record,file=1.silk，magic=true]（发送data\record\1.silk，并标记为变声）
     */
    record("record",
            new String[]{"file", "magic"},
            new String[]{"magic"},
            new String[]{".+", "(true|TRUE|false|FALSE)"},
            5),

    /**
     * [CQ:at,qq={1}] - @某人
     * {1}为被@的群成员QQ。若该参数为all，则@全体成员（次数用尽或权限不足则会转换为文本）。
     * 举例：[CQ:at,qq=123456]
     */
    at("at",
            new String[]{"qq"},
            new String[0],
            new String[]{"(\\d+|all)"},
            6),

    /**
     * 猜拳魔法
     * [CQ:rps,type={1}] - 发送猜拳魔法表情
     * {1}为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * 1 - 猜拳结果为石头
     * 2 - 猜拳结果为剪刀
     * 3 - 猜拳结果为布
     */
    rps("rps",
            new String[]{"type"},
            new String[]{"type"},
            new String[]{"[1-3]"},
            7),

    /**
     * [CQ:dice,type={1}] - 发送掷骰子魔法表情
     * {1}对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     */
    dice("dice",
            new String[]{"type"},
            new String[]{"type"},
            new String[]{"[1-6]"},
            8),

    /**
     * [CQ:shake] - 戳一戳（原窗口抖动，仅支持好友消息使用）
     */
    shake("shake",
            new String[0],
            new String[0],
            new String[0],
            9),

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
            new String[]{"(true|false|ignore)"},
            10),

    /**
     * 音乐
     * [CQ:music,type={1},id={2},style={3}]
     * {1} 音乐平台类型，目前支持qq、163
     * {2} 对应音乐平台的数字音乐id
     * {3} 音乐卡片的风格。仅 Pro 支持该参数，该参数可被忽略。
     * 注意：音乐只能作为单独的一条消息发送
     * 例子
     * [CQ:music,type=qq,id=422594]（发送一首QQ音乐的“Time after time”歌曲到群内）
     * [CQ:music,type=163,id=28406557]（发送一首网易云音乐的“桜咲く”歌曲到群内）
     */
    music("music",
            new String[]{"type", "id", "style"},
            new String[]{"style"},
            new String[]{".+", ".+", ".+"},
            11),

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
            new String[]{"custom", ".+", ".+", ".+", ".+", ".+"},
            12),

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
            new String[]{".+", ".+", ".+", ".+"},
            13),

    /**
     * emoji表情
     */
    emoji("emoji",
            new String[]{"id"},
            new String[0],
            new String[]{"\\d+"},
            14),


    /**
     * 地点
     * [CQ:location,lat={1},lon={2},title={3},content={4}]
     * {1} 纬度
     * {2} 经度
     * {3} 分享地点的名称
     * {4} 分享地点的具体地址
     */
    location("location",
            new String[]{"lat", "lon", "title", "content"},
            new String[0],
            new String[]{"\\d+(\\.\\d+)?", "\\d+(\\.\\d+)?", ".+", ".+"},
            15
    ),

    /**
     * 签到
     * [CQ:sign,location={1},title={2},image={3}]
     * 该CQ码仅支持接收。
     * {1} 用户签到的地点，为中文字串
     * {2} 用户签到时发表的心情文字
     * {3} 签到卡片所使用的背景图片连接
     */
    sign("sign",
            new String[]{"location", "title", "image"},
            new String[0],
            new String[]{".+", ".+", ".+"},
            16),


    ;


    /**
     * 根据function名(即[CQ:后面的名字)获取CQCodeTypes对象实例
     * 已经过时，需要额外通过参数列表进行匹配
     *
     * @param function 名称
     * @return CQCodeTypes实例对象
     * @see #getTypeByFunctionAndParams(String, String...)
     */
    @Deprecated
    public static CQCodeTypes getTypeByFunction(String function) {
        for (CQCodeTypes type : EnumValues.values(CQCodeTypes.class, CQCodeTypes[]::new)) {
            if (type.function.equals(function)) {
                return type;
            }
        }
        return defaultType;
    }

    /**
     * 根据类型和参数名称列表来获取一个具体的枚举类型对象实例
     *
     * @param function   function 值, 即类型，例如"image"或者"share"之类的
     * @param paramNames 参数列表值，需要保证顺序
     * @return CQCodeTypes实例对象, 如果没有则会返回defaultType
     */
    public static CQCodeTypes getTypeByFunctionAndParams(String function, String... paramNames) {
        // 获取对应function的values
        CQCodeTypes[] cqCodeTypes = AllCQCodeTypeMap.get(function);
        if (cqCodeTypes == null || cqCodeTypes.length == 0) {
            return defaultType;
        } else {
            // 筛选paramNames

            // 如果不为null，则遍历并匹配参数match
            for (CQCodeTypes type : cqCodeTypes) {
                // 根据type筛选params并匹配
                if (type.matchKeys(type.selectNeed(paramNames))) {
                    return type;
                }
            }
        }
        return defaultType;
    }


    //**************** 普通常量 ****************//


    /**
     * 类型名
     */
    private final String function;
    /**
     * 参数列表
     */
    private final String[] keys;
    /**
     * 按照索引对应着每个参数的数据类型匹配规则，正则
     */
    private final String[] valuesRegex;
    /**
     * 可以被忽略的参数列表
     */
    private final Set<String> ignoreAbleKeys;
    /**
     * 对此CQ码进行匹配的正则表达式
     */
    private final String matchRegex;
    /**
     * 此CQ码进行匹配的正则表达式的对象
     */
    private final Pattern matchRegexPattern;
    /**
     * 排序用的值
     */
    private final int sort;
    /**
     * 用来判断参数是否匹配的
     * 有时候相同的function可能有不同的两套参数列表，例如music
     * 所以在转化的时候还需要考虑一下参数列表
     */
    private final String paramMatch;

    /**
     * 参数排序，将某个数组根据参数列表进行排序
     */
    private final Function<String[], String[]> paramSort;

    /**
     * 唯一id，通过function:keys判断
     */
    private final String equalsID;

    //**************** 静态常量 ****************//

    /**
     * CQ码匹配的开头，从function开始拼接
     */
    private static final String CQ_REGEX_HEAD = "\\[CQ:((?!(\\[CQ:))";
    /**
     * CQ码匹配的结尾，在最后一个参数后
     */
    private static final String CQ_REGEX_END = ")\\]";
    /**
     * 用于从字符串中提取CQCode码字符串的正则表达式
     */
//    private static final String CQCODE_EXTRACT_REGEX = "\\[CQ:((?!(\\[CQ:)).)+\\]";
    private static final String CQCODE_EXTRACT_REGEX = "\\[CQ:((?!(\\[CQ:))\\w)+\\,((?!(\\[CQ:)).)+\\]";

    /**
     * 内部维护一个Map，key为function，value为CQCodeTypes的数组，并提供一个register函数。
     * 需要保证不会出现重复，则通过function和参数列表判断，参数列表通过set转化后拼接进行比对。
     * 以为set对字符串的排序规则是固定的，则这样拼接出来的必定是唯一的结果。
     * 不考虑可忽略参数
     */
    private static final Map<String, CQCodeTypes[]> AllCQCodeTypeMap = new HashMap<>(32);

    // 静态代码块，注册AllCQCodeTypeMap
    // 这个时候直接使用values吧
    static {
        for (CQCodeTypes value : values()) {
            register(value);
        }
    }

    /**
     * 获取方法类型名称
     */
    public String getFunction() {
        return function;
    }

    /**
     * 获取参数列表
     */
    public String[] getKeys() {
        return keys;
    }

    /**
     * 获取可以忽略的key集合
     */
    public Set<String> getIgnoreAbleKeys() {
        return ignoreAbleKeys;
    }

    /**
     * 获取CQ码全匹配正则
     */
    public static String getCqcodeExtractRegex() {
        return CQCODE_EXTRACT_REGEX;
    }

    /**
     * 获取排序值
     */
    public int getSort() {
        return sort;
    }

    /**
     * 获取某个指定的key的匹配规则
     */
    public String getKeyRegex(String key) {
        if (key == null) return null;

        //遍历key
        for (int i = 0; i < this.keys.length; i++) {
            if (this.keys[i].equals(key)) {
                return this.valuesRegex[i];
            }
        }
        //找不到，返回null
        return null;
    }

    /**
     * 从参数列表中筛选出来非多余的参数
     *
     * @param params 参数列表
     * @return 非多余的列表
     */
    public String[] selectNeed(String... params) {
        // 如果存在key列表且param也不为空
        if ((keys.length > 0) && (params.length > 0)) {
            int count = 0;
            String[] value = new String[keys.length];
            // 遍历所有的params，看看他们是不是在keys的列表里
            for (String param : params) {
                for (String key : keys) {
                    if (param.equals(key)) {
                        value[count++] = param;
                        break;
                    }
                }
            }
            return Arrays.copyOf(value, count);
        } else {
            // 否则直接返回空
            return new String[0];
        }
    }

    public String[] paramSort(String... params) {
        return paramSort.apply(params);
    }

    /**
     * 排序后使用,拼接，用于对参数进行匹配
     *
     * @param params
     * @return
     */
    public String toParamMatch(String... params) {
        return String.join(",", paramSort(params));
    }

    /**
     * 判断参数列表是否符合匹配规则
     *
     * @param keys 参数列表，即key的列表，例如["name", "id"]
     * @return
     */
    public boolean matchKeys(String... keys) {
        return toParamMatch(keys).matches(paramMatch);
    }


    /**
     * 获取匹配字符串
     */
    public String getMatchRegex() {
        return this.matchRegex;
    }

    /**
     * 查看某个字符串是否为此类型的CQ码
     */
    public boolean match(String text) {
        return text.matches(this.matchRegex);
    }

    /**
     * 查看某个字符串中是否存在此类型的CQ码
     */
    public boolean contains(String text) {
//        return text.matches(".*" + this.matchRegex + ".*");
        return matchRegexPattern.matcher(text).find();
    }

    /**
     * 注册一个CQCodeTypes
     *
     * @param newType CQ码类型
     */
    public static synchronized CQCodeTypes register(CQCodeTypes newType) {
        // non null
        Objects.requireNonNull(newType);

        // 获取function
        String function = newType.function;
        // 获取他的参数列表
        CQCodeTypes[] cqCodeTypesWithFunction = AllCQCodeTypeMap.get(function);
        if (cqCodeTypesWithFunction == null) {
            // 如果没用这个function的CQ类型，直接保存一个
            AllCQCodeTypeMap.put(newType.function, new CQCodeTypes[]{newType});
        } else {
            // 如果存在，判断是否有相同ID
            for (CQCodeTypes ct : cqCodeTypesWithFunction) {
                if (ct.equalsID(newType)) {
                    // 如果是等值的，抛出异常
                    throw new IllegalArgumentException(
                            "已经存在此CQ码！\n"
                                    + "function: " + ct.function + '\n'
                                    + "keys:     " + Arrays.toString(ct.keys)
                    );

                }
            }
            // 如果一切安好，则说明可以添加
            // 数组扩容并替换
            CQCodeTypes[] newTypeArray = new CQCodeTypes[cqCodeTypesWithFunction.length + 1];
            System.arraycopy(cqCodeTypesWithFunction, 0, newTypeArray, 0, cqCodeTypesWithFunction.length);
            newTypeArray[cqCodeTypesWithFunction.length] = newType;
            // 保存新的数组
            AllCQCodeTypeMap.put(newType.function, newTypeArray);
        }
        return newType;
    }

    /**
     * 返回某个function下的全部CQCodeTypes
     *
     * @param function function类型
     */
    public static CQCodeTypes[] getCQCodeTypesByFunction(String function) {
        CQCodeTypes[] cqCodeTypes = AllCQCodeTypeMap.get(function);
        return Arrays.copyOf(cqCodeTypes, cqCodeTypes.length);
    }

    /**
     * 判断是否为两个等值的CQ码
     */
    public boolean equalsID(Object o) {
        if (o instanceof CQCodeTypes) {
            return equalsID.equals(((CQCodeTypes) o).equalsID);
        } else {
            return false;
        }
    }

    /**
     * 判断是否存在某个equalsID
     *
     * @param id equalsID
     */
    public boolean containsID(String id) {
        // 遍历values值
        for (CQCodeTypes value : EnumValues.values(CQCodeTypes.class, CQCodeTypes[]::new)) {
            if (value.equalsID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在某个equalsID
     *
     * @param function function值
     * @param keys     keys列表
     */
    public static boolean containsID(String function, String... keys) {
        // 不同于另一个方法，此方法通过AllCQCodeTypeMap判断
        CQCodeTypes[] cqCodeTypes = AllCQCodeTypeMap.get(function);
        if (cqCodeTypes == null || cqCodeTypes.length == 0) {
            return false;
        } else {
            String ID = toEqualsID(function, keys);
            // 查看有没有
            for (CQCodeTypes cqCodeType : cqCodeTypes) {
                if (cqCodeType.equalsID.equals(ID)) {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 根据function和keys构建一个equalsID
     *
     * @param function function
     * @param keys     key数组
     */
    private static String toEqualsID(String function, String[] keys) {
        return function + ":" + Arrays.stream(keys).sorted().collect(Collectors.joining());
    }


    /**
     * 构造方法
     *
     * @param function       cq码类型
     * @param keys           参数列表
     * @param ignoreAbleKeys 可以忽略的参数列表
     * @param valuesRegex    对应参数的参数值匹配正则
     */
    CQCodeTypes(String function, String[] keys, String[] ignoreAbleKeys, String[] valuesRegex, int sort) {
        this.function = function;
        this.keys = keys;
        this.ignoreAbleKeys = Arrays.stream(ignoreAbleKeys).collect(Collectors.toSet());
        this.valuesRegex = valuesRegex;
        this.sort = sort;
//        this.equalsID = function + ":" + Arrays.stream(keys).sorted().collect(Collectors.joining());
        this.equalsID = toEqualsID(function, keys);

        //生成匹配正则表达式
        StringJoiner joiner = new StringJoiner("", CQ_REGEX_HEAD + this.function, CQ_REGEX_END);
        //遍历keys
        for (int i = 0; i < keys.length; i++) {
            String key = this.keys[i];
            String regex = this.valuesRegex[i];
            boolean ignoreAble = this.ignoreAbleKeys.contains(key);
            //如果可忽略，使用括号括住并在最后加上?
            String in = "," + key + "=" + regex;
            if (ignoreAble)
                in = ("(" + in + ")?");

            joiner.add(in);
        }

        //遍历完成，生成字符串
        this.matchRegex = joiner.toString();
        //创建封装类
        this.matchRegexPattern = RegexUtil.getPattern(matchRegex);

        // 生成参数匹配字符串
        // 大概规则：
        // 参数1,参数2(,可忽略参数3)
        StringBuilder paramMatchBuilder = new StringBuilder();
        boolean first = true;
        for (String key : keys) {
            // 判断是否可以忽略
            boolean ignore = this.ignoreAbleKeys.contains(key);
            if (ignore) {
                // 可以省略
                paramMatchBuilder.append("(");
                if (!first) {
                    paramMatchBuilder.append(",");
                }
                paramMatchBuilder.append(key);
                paramMatchBuilder.append(")?");
            } else {
                // 不可省略, 则不带括号
                if (!first) {
                    paramMatchBuilder.append(",");
                }
                paramMatchBuilder.append(key);
            }
            first = false;
        }
        this.paramMatch = paramMatchBuilder.toString();

        // 生成参数排序函数
        int paramSort = 1;
        Map<String, Integer> sortMap = new HashMap<>(keys.length);
        for (String key : keys) {
            sortMap.put(key, paramSort++);
        }

        this.paramSort = srr -> {
            String[] sortArray = Arrays.copyOf(srr, srr.length);
            Arrays.sort(sortArray, Comparator.comparing(s -> sortMap.getOrDefault(s, -1)));
            return sortArray;
        };

        // 注册自己
        // 根据枚举反编译规则，不能直接在构造中注册自己
//        register(this);
    }

}
