package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.ExplainPlanProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ExplainPlanPropertiesIT extends AbstractIT {

    @Autowired
    private ExplainPlanProperties explainPlanProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(explainPlanProperties.isEnabled()).isTrue();
        assertThat(explainPlanProperties.isIgnoreBatch()).isFalse();
    }

}
