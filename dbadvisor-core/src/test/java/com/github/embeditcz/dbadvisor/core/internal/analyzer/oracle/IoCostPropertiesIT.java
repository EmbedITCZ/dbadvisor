package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IoCostPropertiesIT extends AbstractIT {

    @Autowired
    private IoCostProperties ioCostProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(ioCostProperties.isEnabled()).isTrue();
        assertThat(ioCostProperties.getThreshold()).isEqualTo(1000);
    }
}