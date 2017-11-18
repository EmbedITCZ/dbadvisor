package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class IoCostProperties {

    private final boolean enabled;
    private final long threshold;

    public IoCostProperties(
            @Value("${dbadvisor.oracle.io-cost.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.io-cost.threshold:1000}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
