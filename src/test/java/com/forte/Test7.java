package com.forte;

import com.forte.config.ConfigurationHelper;
import com.forte.config.InjectableConfig;
import com.forte.qqrobot.BaseApplication;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

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
