package com.github.embeditcz.dbadvisor.integrationtest;

import com.github.embeditcz.dbadvisor.core.internal.DbAdvisorProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DbAdvisorPropertiesIT extends AbstractIT {

    @Autowired
    private DbAdvisorProperties properties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(properties.isReportOnShutdownHook()).isTrue();
        assertThat(properties.getReportName()).isEqualTo("dbadvisor");
        assertThat(properties.getReportLocation()).isEqualTo(System.getProperty("java.io.tmpdir"));
        assertThat(properties.getMaxIssuesCountPerType()).isEqualTo(100);
    }

}
