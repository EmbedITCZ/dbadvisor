package com.github.embeditcz.dbadvisor.core;

import com.github.embeditcz.dbadvisor.core.AbstractIT.TestConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public abstract class AbstractIT {

    @Configuration
    @EnableDbAdvisor
    @EnableAutoConfiguration
    public static class TestConfig {

        @Bean
        public HikariDataSource dataSource() {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:oracle:thin:@//dockerip:1531/ORCL"); // TODO tmp
            ds.setUsername("SAA");
            ds.setPassword("p1");
            return ds;
        }

    }

}
