package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
