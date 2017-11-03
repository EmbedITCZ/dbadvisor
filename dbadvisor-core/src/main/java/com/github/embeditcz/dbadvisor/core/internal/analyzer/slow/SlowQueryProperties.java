package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class SlowQueryProperties {

    private final boolean enabled;
    private final boolean ignoreBatch;
    private final long timeThreshold;

    public SlowQueryProperties(
            @Value("${dbadvisor.slowQuery.enabled:true}") boolean enabled,
            @Value("${dbadvisor.slowQuery.ignoreBatch:false}") boolean ignoreBatch,
            @Value("${dbadvisor.slowQuery.timeThreshold:1000}") long timeThreshold) {
        this.enabled = enabled;
        this.ignoreBatch = ignoreBatch;
        this.timeThreshold = timeThreshold;
    }

}
