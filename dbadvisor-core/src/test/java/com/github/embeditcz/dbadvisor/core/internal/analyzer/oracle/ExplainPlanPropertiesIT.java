package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExplainPlanPropertiesIT extends AbstractIT {

    @Autowired
    private ExplainPlanProperties explainPlanProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(explainPlanProperties.isEnabled()).isTrue();
        assertThat(explainPlanProperties.isIgnoreBatch()).isFalse();
    }

}
