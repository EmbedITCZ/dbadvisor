package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CpuCostProperties {

    private final boolean enabled;
    private final long threshold;

    public CpuCostProperties(
            @Value("${dbadvisor.oracle.cpuCost.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.cpuCost.threshold:100000}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
