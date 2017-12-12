package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.ForeignKeyProperties;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ForeignKeyPropertiesIT extends AbstractIT {

    @Autowired
    private ForeignKeyProperties foreignKeyProperties;

    @Test
    public void shouldUseDefaultValues() {
        assertThat(foreignKeyProperties.isEnabled()).isFalse();
        assertThat(foreignKeyProperties.getThreshold()).isEqualTo(1);
    }

}
