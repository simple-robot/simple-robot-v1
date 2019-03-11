package com.forte.qqrobot.utils.scanner;

import java.util.List;

/**
 * 包扫描工具
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/9 17:09
 * @since JDK1.8
 **/
public class ClassScanner extends BasePackageScanner {

    /** 保存Class的list集合 */
    private List<Class> classList;

    /**
     * 将每扫描到的class保存到list
     * @param klass
     */
    @Override
    public void dealClass(Class<?> klass) {
        classList.add(klass);
    }



    /**
     * 构造
     * @param classList
     */
    public ClassScanner(List<Class> classList){
        this.classList = classList;
    }

}
