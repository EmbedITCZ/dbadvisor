package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.CostBaseProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CostBasePropertiesIT extends AbstractIT {

    @Autowired
    private CostBaseProperties costBaseProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(costBaseProperties.isEnabled()).isTrue();
        assertThat(costBaseProperties.getThreshold()).isEqualTo(100);
    }
}
