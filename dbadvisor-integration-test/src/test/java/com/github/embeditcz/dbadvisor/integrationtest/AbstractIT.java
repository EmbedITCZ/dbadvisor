package com.github.embeditcz.dbadvisor.integrationtest;

import com.github.embeditcz.dbadvisor.core.EnableDbAdvisor;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public abstract class AbstractIT {

    @Configuration
    @EnableDbAdvisor
    @EnableAutoConfiguration
    public static class TestConfig {
    }

}
