package com.github.embeditcz.dbadvisor.core.internal;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DbAdvisorDataSourceProxyCreatorIT extends AbstractIT {

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldWrapDataSource() {
        assertThat(dataSource).isInstanceOf(ProxyDataSource.class);
    }

}
