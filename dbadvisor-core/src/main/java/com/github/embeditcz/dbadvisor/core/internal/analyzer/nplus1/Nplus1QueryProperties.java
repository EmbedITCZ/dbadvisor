package com.github.embeditcz.dbadvisor.core.internal.analyzer.nplus1;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class Nplus1QueryProperties {

    private final boolean enabled;
    private final long threshold;

    public Nplus1QueryProperties(
            @Value("${dbadvisor.nplus1.enabled:true}") boolean enabled,
            @Value("${dbadvisor.nplus1.threshold:10}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
