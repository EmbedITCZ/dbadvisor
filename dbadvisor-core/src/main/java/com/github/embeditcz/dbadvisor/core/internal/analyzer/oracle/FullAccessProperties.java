package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FullAccessProperties {

    private final boolean enabled;
    private final long threshold;

    public FullAccessProperties(
            @Value("${dbadvisor.oracle.fullAccess.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.fullAccess.threshold:1}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
