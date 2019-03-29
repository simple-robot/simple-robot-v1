package com.forte.qqrobot.utils;

import com.forte.qqrobot.beans.CQCode;
import com.forte.qqrobot.beans.types.CQCodeTypes;
import sun.java2d.pipe.SpanIterator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CQ码生成器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 14:37
 * @since JDK1.8
 **/
public class CQCodeUtil {

    /** CQ码开头 */
    private static final String CQ_CODE_HEAD = "[CQ:";
    /** CQ码结尾 */
    private static final String CQ_CODE_END = "]";
    /** CQ码k-v分割符 */
    private static final String CQ_CODE_SPLIT = ",";

    /**
     * 获取CQ码生成用StringJoiner
     * @return
     */
    private static StringJoiner getCQCodeJoiner(){
        return new StringJoiner(CQ_CODE_SPLIT, CQ_CODE_HEAD, CQ_CODE_END);
    }

    /**
     * 字符串转义-不在CQ码内的消息
     * 对于不在CQ码内的消息（即文本消息），为了防止解析混淆，需要进行转义。
     * 转义规则如下：
     * & -> &amp;
     * [ -> &#91;
     * ] -> &#93;
     * @return 转义后的字符串
     */
    public String escapeOutCQCode(String msgOutCQCode){
        return msgOutCQCode
                .replaceAll("\\&" , "&amp;")
                .replaceAll("\\[" , "&#91;")
                .replaceAll("\\]" , "&#93;")
                ;
    }

    /**
     * 对于CQ码中的value（参数值），为了防止解析混淆，需要进行转义。
     * 此方法大概仅需要内部使用
     * 转义规则如下：
     * & -> &amp;
     * [ -> &#91;
     * ] -> &#93;
     * , -> &#44;
     * @param value
     * @return
     */
    private String escapeValue(String value){
        return value
                .replaceAll("\\&" , "&amp;")
                .replaceAll("\\[" , "&#91;")
                .replaceAll("\\]" , "&#93;")
                .replaceAll("\\," , "&#44;")
                ;
    }


    /**
     * CQCode字符串生成
     * @param type      CQCode类型
     * @param params    参数，将会根据现有的参数按照对应的索引进行赋值，
     *                  如果对应索引为null则视为忽略参数
     * @return  CQCode字符串
     */
    private String getCQCodeString(CQCodeTypes type, String... params){
        Objects.requireNonNull(params);
        //获取此CQ码的类型
        String function = type.getFunction();
        //获取joiner
        StringJoiner joiner = getCQCodeJoiner();
        joiner.add(function);
        //获取参数列表
        String[] keys = type.getKeys();
        //理论上，参数的数量应该不会小于keys的数量，如果长度不足，以null补位

        //遍历参数列表
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            //获取value值并转义，如果参数不存在则标记为null
            //如果参数的索引不足类型的索引，以null补位
            String value = Optional.ofNullable(params.length >= (i+1) ? params[i] : null).map(this::escapeValue).orElse(null);
            //如果参数不为null则说明不需要忽略
            if(value != null){
                joiner.add(key+"="+value);
            }
        }
        return joiner.toString();
    }


    /* ———————————————————— CQ码获取方法 ———————————————————— */

    /**
     * 获取emoji字符表情
     * @param id  moji字符的unicode编号
     * @return    emoji的CQ码
     */
    public String getCQCode_face(String id){
        return getCQCodeString(CQCodeTypes.face, id);
    }

    /**
     * 获取原创表情
     * @param id 原创表情的ID
     * @return  原创表情CQ
     */
    public String getCQCode_bface(String id){
        return getCQCodeString(CQCodeTypes.bface, id);
    }

    /**
     * 小表情
     * @param id 小表情的ID
     * @return 小表情CQ
     */
    public String getCQCode_sface(String id){
        return getCQCodeString(CQCodeTypes.sface, id);
    }

    /**
     * 发送自定义图片
     * @param file  图片文件名称
     * @return      自定义图片CQ
     */
    public String getCQCode_image(String file){
        return getCQCodeString(CQCodeTypes.image, file);
    }

    /**
     * 发送语音
     * @param file  音频文件名称
     * @param magic 是否为变声
     * @return  发送语音CQ
     */
    public String getCQCode_record(String file, Boolean magic){
        return getCQCodeString(CQCodeTypes.record, file, Optional.ofNullable(magic).map(Object::toString).orElse(null));
    }

    /**
     * 发送语音
     * @param file  音频文件名称
     * @return  发送语音CQ
     */
    public String getCQCode_record(String file){
        return getCQCodeString(CQCodeTypes.record, file, null);
    }

    /**
     * at某人
     * @param qq qq号
     * @return  at CQcode
     */
    public String getCQCode_at(String qq){
        return getCQCodeString(CQCodeTypes.at, qq);
    }

    /**
     * 发送猜拳魔法表情
     * @param type  为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * @return  猜拳魔法表情CQCode
     */
    public String getCQCode_rps(String type){
        return getCQCodeString(CQCodeTypes.rps, type);
    }

    /**
     * 掷骰子魔法表情
     * @param type 对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     * @return  掷骰子魔法表情CQCode
     */
    public String getCQCode_dice(String type){
        return getCQCodeString(CQCodeTypes.dice, type);
    }

    /**
     * 戳一戳 仅支持好友消息使用
     * @return 戳一戳CQCode
     */
    public String getCQCode_shake(){
        return getCQCodeString(CQCodeTypes.shake);
    }

    /**
     * 匿名发消息（仅支持群消息使用）
     * 本CQ码需加在消息的开头。
     * @param ignore 当{1}为true时，代表不强制使用匿名，如果匿名失败将转为普通消息发送。
     *               当{1}为false或ignore参数被忽略时，代表强制使用匿名，如果匿名失败将取消该消息的发送。<br>
     * @return 匿名发消息
     */
    public String getCQCode_anonymous(Boolean ignore){
        return getCQCodeString(CQCodeTypes.anonymous, Optional.ofNullable(ignore).map(Object::toString).orElse(null));
    }

    /**
     * 匿名发消息（仅支持群消息使用）
     * 本CQ码需加在消息的开头。
     * 参数被忽略，代表强制使用匿名，如果匿名失败将取消该消息的发送。
     * @return 匿名发消息
     */
    public String getCQCode_anonymous(){
        return getCQCodeString(CQCodeTypes.anonymous, null);
    }


    /**
     * 发送音乐
     * 注意：音乐只能作为单独的一条消息发送
     * @param type  为音乐平台类型，目前支持qq、163、xiami
     * @param id    为对应音乐平台的数字音乐id
     * @return 发送音乐
     */
    public String getCQCode_music(String type, String id){
        return getCQCodeString(CQCodeTypes.music, type, id);
    }

    /**
     * 发送音乐自定义分享
     * @param url       为分享链接，即点击分享后进入的音乐页面（如歌曲介绍页）。
     * @param audio     为音频链接（如mp3链接）。
     * @param title     为音乐的标题，建议12字以内。
     * @param content   为音乐的简介，建议30字以内。该参数可被忽略。
     * @param image     为音乐的封面图片链接。若参数为空或被忽略，则显示默认图片。
     * @return  音乐自定义分享CQCode
     */
    public String getCQCode_music_custom(String url, String audio, String title, String content, String image){
        return getCQCodeString(CQCodeTypes.music_custom,"custom", url, audio, title, content, image);
    }

    /**
     * 发送链接分享
     * 注意：链接分享只能作为单独的一条消息发送
     * @param url       为分享链接。
     * @param title     为分享的标题，建议12字以内。
     * @param content   为分享的简介，建议30字以内。该参数可被忽略。
     * @param image     为分享的图片链接。若参数为空或被忽略，则显示默认图片。
     * @return  链接分享CQCode
     */
    public String getCQCode_share(String url, String title, String content, String image){
        return getCQCodeString(CQCodeTypes.share, url, title, content, image);
    }

    //**************** CQ码辅助方法 ****************//


    /** 用于从字符串中提取CQCode码字符串的正则表达式 */
    private static final String CQCODE_EXTRACT_REGEX = CQCodeTypes.getCqcodeExtractRegex();


    /**
     * 从信息字符串中提取出CQCode码的字符串
     * @param msg 信息字符串
     * @return 提取出CQCode码的字符串
     */
    public List<String> getCQCodeStrFromMsg(String msg){
        return RegexUtil.getMatcher(msg, CQCODE_EXTRACT_REGEX);
    }


    /**
     * 从信息字符串中移除CQCode字符串
     * @param msg 字符串
     * @return 移除后的字符串
     */
    public String removeCQCodeFromMsg(String msg){
        return removeCQCodeFromMsg(msg, null);
    }

    /**
     * 从信息字符串中移除某种类型的CQCode字符串
     * @param msg 字符串
     * @param cqCodeTypes CQ码类型
     * @return 移除后的字符串
     */
    public String removeCQCodeFromMsg(String msg, CQCodeTypes cqCodeTypes){
        return msg.replaceAll(cqCodeTypes == null ? CQCODE_EXTRACT_REGEX : cqCodeTypes.getMatchRegex(), "");
    }

    /**
     * 从信息字符串中提取出CQCode码对象
     * @param msg 信息字符串 如果为空则返回空字符串
     * @return 提取出CQCode码对象
     */
    public List<CQCode> getCQCodeFromMsg(String msg){
        if(msg == null || msg.trim().length() <= 0){
            return Collections.emptyList();
        }
        //CQ码list集合
        List<String> cqStrList = getCQCodeStrFromMsg(msg);
        return cqStrList.stream()
                //移除[CQ:和],在以逗号分隔
                .map(s -> s.substring(4, s.length() - 1).split("\\,"))
                .map(arr -> {
                    //参数数组
                    String[] paramArr = new String[arr.length - 1];
                    System.arraycopy(arr, 1, paramArr, 0, paramArr.length);
                    return CQCode.of(arr[0], paramArr);
                }).collect(Collectors.toList());
    }

    /**
     * 判断是否存在at某个qq
     * @return 是否at了某个qq
     */
    public boolean isAt(String msg, String qq){
        if(msg == null){
            return false;
        }
        //如果存在at的CQ码并且参数‘qq’是某个qq
        return getCQCodeFromMsg(msg).stream().anyMatch(cq -> cq.getCQCodeTypes().equals(CQCodeTypes.at) && cq.getParams().get("qq").equals(qq));
    }

    /**
     * 判断某个字符串中是否存在某类型的CQ码
     * @param types CQ码类型
     * @param text  字符串
     * @return 是否包含
     */
    public boolean isContains(CQCodeTypes types, String text){
        return types.contains(text);
    }


}
