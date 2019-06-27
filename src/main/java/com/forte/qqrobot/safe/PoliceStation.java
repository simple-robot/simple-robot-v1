package com.forte.qqrobot.safe;

import com.forte.qqrobot.exception.safe.SRSafeError;
import sun.reflect.Reflection;

import java.util.HashMap;


/**
 * 用于确保安全的警察局，此类将会用于预防反射等可能会破坏框架结构的行为
 * （未完成）
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public final class PoliceStation {

    /** 本类类名 */
    private static final String POLICE_CLASS_NAME = "com.forte.qqrobot.safe.PoliceStation";

    /** 绝对单例 */
    private static final PoliceStation POLICE_STATION;

    /** 初始可以获取实例的类 */
    private static final String[] initAbleClassName = {
            "com.forte.qqrobot.BaseConfiguration",
            "com.forte.qqrobot.BaseApplication",
            POLICE_CLASS_NAME,
            "com.forte.test.Test4"
    };

    /** 规定key：某个类.方法, value：这个方法可以被那些范围内的类中的方法使用，如果方法为*则只判断类
     *  此Map应当不允许移除元素
     * */
    private static final HashMap<String, String[]> whoInvokeWho = new HashMap<>();


    /** 可以获取此类实例的类 */
//    private static final Set<String> ableClassName = new HashSet<>();

    /** 静态代码块 */
    static{
        //反射保护
        Reflection.registerFieldsToFilter(PoliceStation.class,
                "POLICE_CLASS_NAME",
                "POLICE_STATION",
                "initAbleClassName",
                "ableClassName",
                "whoInvokeWho"
        );
        Reflection.registerMethodsToFilter(PoliceStation.class,
                "getInstance",
                "error",
                "whoRunMe",
                "whoRunYou"
        );

        POLICE_STATION = new PoliceStation();

    }

    /** 单例异常构造 */
    private PoliceStation(){
        if(POLICE_STATION != null){
            error("请不要尝试去创建本类的额外实例。\r\n" +
                    "创建"+ POLICE_CLASS_NAME +"实例没有任何意义。");
        }
    }

    //**************** 方法 ****************//
    
    
    /** 只有指定的类可以获取此类 */
    public static PoliceStation getInstance(){
            return whoRunMe() ? POLICE_STATION : error("不被允许的范围内获取实例");
    }

    /**
     * 抛出异常
     */
    private static <T> T error(String msg){
        throw new SRSafeError(msg);
    }

    /**
     * 此方法只能本类中使用。
     * 以用作安全检测
     */
    private static boolean whoRunMe(){
        final String thisClassName = "com.forte.qqrobot.safe.PoliceStation";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //判断调用此方法的类是否为本类
        StackTraceElement me = stackTrace[2];
        if(!me.getClassName().equals(thisClassName)){
            return error("请不要尝试利用反射在非允许类中执行本类方法。");
        }

        StackTraceElement from = stackTrace[3];
        String className = from.getClassName();
        String methodName = from.getMethodName();
        int lineNumber = from.getLineNumber();

        for (String ableName : initAbleClassName) {
            if(ableName.equals(className)){
                return true;
            }
        }
        //如果不被允许，抛出异常
        return error('[' + className + "]不被允许使用["+ POLICE_CLASS_NAME +"]类。["+ methodName +"(line:"+lineNumber+")]");
    }


    /**
     * 此方法不允许在本方法中使用。
     * 检测调用此方法的方法的调用者是否在常规中。
     */
    public static boolean whoRunYou(){
        final String thisClassName = "com.forte.qqrobot.safe.PoliceStation";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //执行本方法的方法
        StackTraceElement invokeMe = stackTrace[2];
        if(invokeMe.getClassName().equals(thisClassName)){
            return error("方法whoRunYou()不允许在" + thisClassName + "类中使用。");
        }

        // 执行本方法的方法
        String invokeMeMethod = invokeMe.getClassName() + '.' + invokeMe.getMethodName();

        //先判断这个类是否在范围内
        if(whoRunMe() && stackTrace.length > 3){
            //如果在范围内，判断调用它的方法
            StackTraceElement from = stackTrace[3];
            String fromMethod = from.getClassName() + '.' + from.getMethodName();
            String[] strings = whoInvokeWho.get(invokeMeMethod);
            if(strings == null){
                return false;
            }else{
                //存在, 遍历判断
                //全匹配符
                String allAgree = from.getClassName() + ".*";
                for (String agree : strings) {
                    if(agree.equals(allAgree) || agree.equals(fromMethod)){
                        return true;
                    }
                }
                return false;
            }
        }else{
             return error("虽然不是很可能，但是你已经没有上一级调用者了");
        }
    }

}
