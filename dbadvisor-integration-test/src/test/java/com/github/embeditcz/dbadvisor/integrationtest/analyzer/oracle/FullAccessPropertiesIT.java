package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.FullAccessProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FullAccessPropertiesIT extends AbstractIT {

    @Autowired
    private FullAccessProperties fullAccessProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(fullAccessProperties.isEnabled()).isTrue();
        assertThat(fullAccessProperties.getThreshold()).isEqualTo(1);
    }
}
