package com.github.embeditcz.dbadvisor.core.internal;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DbAdvisorProperties {

    private final String reportName;
    private final String reportLocation;
    private final boolean reportOnShutdownHook;
    private final int maxIssuesCountPerType;

    public DbAdvisorProperties(
        @Value("${dbadvisor.report.name:dbadvisor}") String reportName,
        @Value("${dbadvisor.report.location:#{systemProperties['java.io.tmpdir']}}") String reportLocation,
        @Value("${dbadvisor.report.on-shutdown-hook:true}") boolean reportOnShutdownHook,
        @Value("${dbadvisor.max-issues-count-per-type:100}") int maxIssuesCountPerType) {
        this.reportName = reportName;
        this.reportLocation = reportLocation;
        this.reportOnShutdownHook = reportOnShutdownHook;
        this.maxIssuesCountPerType = maxIssuesCountPerType;
    }

}
