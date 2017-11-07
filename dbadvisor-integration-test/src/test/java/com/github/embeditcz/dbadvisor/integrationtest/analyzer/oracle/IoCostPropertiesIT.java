package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.IoCostProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class IoCostPropertiesIT extends AbstractIT {

    @Autowired
    private IoCostProperties ioCostProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(ioCostProperties.isEnabled()).isTrue();
        assertThat(ioCostProperties.getThreshold()).isEqualTo(1000);
    }
}
