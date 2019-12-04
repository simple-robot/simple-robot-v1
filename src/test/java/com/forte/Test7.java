package com.forte;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;
import com.forte.qqrobot.BaseApplication;
import com.forte.testrun.TestApplication;
import com.forte.testrun.TestConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test7 {
    public static void main(String[] args) throws IOException {
        InjectableConfig<TestConfig> testInj = ConfigurationHelper.toInjectable(TestConfig.class);

        InputStream proIn = BaseApplication.class.getResourceAsStream("/conf.properties");

        Properties properties = new Properties();
        properties.load(proIn);

        System.out.println(proIn);

        TestConfig config = new TestConfig();

        TestApplication testApplication = new TestApplication();
        testApplication.before(config);

        System.out.println(config.isLocalServerEnable());


    }
}
