package com.github.embeditcz.dbadvisor.integrationtest.analyzer.nplus1;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.nplus1.Nplus1QueryProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class Nplus1QueryPropertiesIT extends AbstractIT {

    @Autowired
    private Nplus1QueryProperties nplus1QueryProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(nplus1QueryProperties.isEnabled()).isTrue();
        assertThat(nplus1QueryProperties.getThreshold()).isEqualTo(10);
    }

}
