package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class CostBaseProperties {

    private final boolean enabled;
    private final long threshold;

    public CostBaseProperties(
            @Value("${dbadvisor.oracle.costBase.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.costBase.threshold:100}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
