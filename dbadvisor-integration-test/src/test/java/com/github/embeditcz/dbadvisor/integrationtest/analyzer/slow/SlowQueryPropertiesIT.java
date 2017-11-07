package com.github.embeditcz.dbadvisor.integrationtest.analyzer.slow;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.slow.SlowQueryProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SlowQueryPropertiesIT extends AbstractIT {

    @Autowired
    private SlowQueryProperties slowQueryProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(slowQueryProperties.isEnabled()).isTrue();
        assertThat(slowQueryProperties.isIgnoreBatch()).isFalse();
        assertThat(slowQueryProperties.getThreshold()).isEqualTo(1000);
    }

}
