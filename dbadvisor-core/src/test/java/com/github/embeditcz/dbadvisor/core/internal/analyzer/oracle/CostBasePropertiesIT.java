package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CostBasePropertiesIT extends AbstractIT {

    @Autowired
    private CostBaseProperties costBaseProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(costBaseProperties.isEnabled()).isTrue();
        assertThat(costBaseProperties.getThreshold()).isEqualTo(100);
    }
}