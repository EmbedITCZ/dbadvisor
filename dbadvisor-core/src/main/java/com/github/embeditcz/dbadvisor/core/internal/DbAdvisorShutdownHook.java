package com.github.embeditcz.dbadvisor.core.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@Component
@RequiredArgsConstructor
class DbAdvisorShutdownHook {

    private static final Logger logger = Logger.getLogger("dbadvisor");

    private final DbAdvisorProperties properties;
    private final DbAdvisorReportGenerator reportGenerator;

    @PostConstruct
    public void init() {
        if (properties.isReportOnShutdownHook()) {
            logger.log(INFO, "Registering shutdown hook");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    reportGenerator.generate();
                }
            });
        }
    }

}
