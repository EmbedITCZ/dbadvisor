package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class IoCostProperties {

    private final boolean enabled;
    private final long threshold;

    public IoCostProperties(
            @Value("${dbadvisor.oracle.ioCost.enabled:true}") boolean enabled,
            @Value("${dbadvisor.oracle.ioCost.threshold:1000}") long threshold) {
        this.enabled = enabled;
        this.threshold = threshold;
    }

}
