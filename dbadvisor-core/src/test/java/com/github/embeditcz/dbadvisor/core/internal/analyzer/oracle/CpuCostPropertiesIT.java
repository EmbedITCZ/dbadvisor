package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CpuCostPropertiesIT extends AbstractIT {

    @Autowired
    private CpuCostProperties cpuCostProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(cpuCostProperties.isEnabled()).isTrue();
        assertThat(cpuCostProperties.getThreshold()).isEqualTo(100000);
    }
}