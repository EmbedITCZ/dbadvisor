package com.github.embeditcz.dbadvisor.integrationtest;

import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DbAdvisorDataSourceProxyCreatorIT extends AbstractIT {

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldWrapDataSource() {
        assertThat(dataSource).isInstanceOf(ProxyDataSource.class);
    }

}
