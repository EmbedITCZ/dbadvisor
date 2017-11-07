package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SlowQueryProperties {

    private final boolean enabled;
    private final boolean ignoreBatch;
    private final long threshold;

    public SlowQueryProperties(
            @Value("${dbadvisor.slowQuery.enabled:true}") boolean enabled,
            @Value("${dbadvisor.slowQuery.ignoreBatch:false}") boolean ignoreBatch,
            @Value("${dbadvisor.slowQuery.threshold:1000}") long threshold) {
        this.enabled = enabled;
        this.ignoreBatch = ignoreBatch;
        this.threshold = threshold;
    }

}
