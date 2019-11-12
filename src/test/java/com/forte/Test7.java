package com.forte;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;
import com.forte.qqrobot.BaseApplication;
import org.apache.commons.beanutils.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test7 {
    public static void main(String[] args) throws IOException {
//        InjectableConfig<TestConfig> testInj = ConfigurationHelper.toInjectable(TestConfig.class);
//
//        InputStream proIn = BaseApplication.class.getResourceAsStream("/conf.properties");
//
//        Properties properties = new Properties();
//        properties.load(proIn);
//
//        TestConfig config = new TestConfig();
//
//        System.out.println(config);
//
//        testInj.inject(config, properties);
//
//        System.out.println(config);

        String[] convert = (String[]) ConvertUtils.convert("1,2,3,4", String[].class);

        System.out.println(Arrays.toString(convert));

    }
}
