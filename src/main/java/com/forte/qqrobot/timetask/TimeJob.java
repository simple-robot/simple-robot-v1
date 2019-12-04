package com.forte.qqrobot.timetask;

import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.depend.Depend;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.FieldUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Objects;

/**
 *
 * 实现Job接口并提供了一个新的接口以提供相关功能。<br>
 * 假如要实现依赖注入功能，则必须携带@Beans注解。
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface TimeJob extends Job {

    /** 同名方法，获取并注入两个定时任务参数 */
    void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil);

    /** 仅用于获取定时任务参数 */
    @Override
    default void execute(JobExecutionContext context) {
       try{
           CQCodeUtil cqCodeUtil = TimeTaskContext.getCQCodeUtil(context);
           MsgSender msgSender = TimeTaskContext.getMsgSender(context);

           // 如果此类存在Beans对象，则获取
           Beans beans = getBeans();

           if(beans != null){
               try{
                   DependCenter center = TimeTaskContext.getDependCenter(context);
                   Depend<TimeJob> depend = getDepend((String) null, (Class<TimeJob>) getClass(), beans, center);
                   if(depend != null){
                       depend.inject(this);
                   }
               }catch (Exception e){
                   QQLog.error("定时任务["+this.getClass()+"]依赖注入异常: ", e);
               }
           }
           execute(msgSender, cqCodeUtil);
       }catch (Exception e){
           throw new TimeTaskException(e);
       }
    }

    /**
     * 获取Beans注解，当返回值不为null的时候会进行注入
     */
    default Beans getBeans(){
        // 获取此类的Beans注解
        return AnnotationUtils.getAnnotation(getClass(), Beans.class);
    }

    /**
     * 提供一个对象，他的@Beans注解对象以及依赖工厂，则如果可以，则。
     * 为了考虑可能存在的优化方案，仅有当参数@Beans注解对象为null的时候才会通过反射去获取注解。
     * @param beanName          定时任务对象依赖名称
     * @param type              定时任务类型, 不可为null
     * @param beans             @Beans注解对象
     * @param center            依赖工厂，如果为null则方法失效
     * @return 获取TimeJob对应的Depend对象，可能为null。
     */
    static <T extends TimeJob> Depend<T> getDepend(String beanName , Class<T> type, Beans beans, DependCenter center){
        // 如果center对象为null，则取消注入
        if(center == null){
            return null;
        }
        Objects.requireNonNull(type);

        // 如果没有注解，获取注解
        if(beans == null){
            beans = AnnotationUtils.getAnnotation(type, Beans.class);
            if(beans == null){
                // 如果尝试获取了还是null，则不再注入。
                return null;

            }
        }

        // 如果没有名称，获取名称
        if(beanName == null || beanName.trim().length() == 0){
            beanName = beans.value();
            if(beanName.trim().length() == 0){
                // 如果value空，则为类型的类名，开头小写
                beanName = FieldUtils.headLower(type.getSimpleName());
            }
        }

        // 通过名称和类型获取依赖对象
        return center.getDepend(beanName, type);
    }



}
