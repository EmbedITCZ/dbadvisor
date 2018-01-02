package com.github.embeditcz.dbadvisor.integrationtest;

import com.github.embeditcz.dbadvisor.core.EnableDbAdvisor;
import com.github.embeditcz.dbadvisor.core.datasource.DefaultPrivilegedDataSourceProvider;
import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceProvider;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT.TestConfig;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public abstract class AbstractIT {

    @Configuration
    @EnableDbAdvisor
    @EnableAutoConfiguration
    public static class TestConfig {

        static {
            // during tests explicitly disable this verifier
            System.setProperty("dbadvisor.oracle.foreign-key.enabled", "false");
        }

        @Bean
        PrivilegedDataSourceProvider privilegedBankDataSource() throws SQLException {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setUser("system");
            dataSource.setPassword("oracle");
            dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/XE");

            return DefaultPrivilegedDataSourceProvider.builder()
                .dataSourceName("dataSource")
                .privilegedDataSource(dataSource)
                .schemas(Arrays.asList("BANK"))
                .build();
        }

    }

}
