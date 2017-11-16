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
            @Value("${dbadvisor.oracle.full-access.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.full-access.threshold:1}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
