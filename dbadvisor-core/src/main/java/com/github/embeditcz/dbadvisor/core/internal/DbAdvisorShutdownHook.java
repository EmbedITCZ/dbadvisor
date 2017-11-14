package com.github.embeditcz.dbadvisor.core.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.logging.Level.INFO;

@Log
@Component
@RequiredArgsConstructor
class DbAdvisorShutdownHook {

    private final DbAdvisorProperties properties;
    private final DbAdvisorReportGenerator reportGenerator;

    @PostConstruct
    public void init() {
        if (properties.isReportOnShutdownHook()) {
            log.log(INFO, "Registering shutdown hook");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    reportGenerator.generate();
                }
            });
        }
    }

}
