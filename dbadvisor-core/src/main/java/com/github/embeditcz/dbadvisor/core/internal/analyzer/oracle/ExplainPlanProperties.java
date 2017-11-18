package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExplainPlanProperties {

    private final boolean enabled;
    private final boolean ignoreBatch;

    public ExplainPlanProperties(
            @Value("${dbadvisor.oracle.execution-plan.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.execution-plan.ignore-batch:false}") boolean ignoreBatch) {
        this.enabled = enabled;
        this.ignoreBatch = ignoreBatch;
    }

}
