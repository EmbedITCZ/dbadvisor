package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.internal.issue.IssueObjectMapper;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@Log
@Component
@RequiredArgsConstructor
class DbAdvisorReportGenerator {

    private final DbAdvisorProperties properties;
    private final IssueRepository issueRepository;
    private final IssueObjectMapper issueObjectMapper;

    public void generate() {
        generateJsonReport();
    }

    private void generateJsonReport() {
        try {
            File reportFile = resolveJsonReportFile();
            List<Issue> issues = issueRepository.getIssues();
            issueObjectMapper.getObjectMapper().writeValue(reportFile, issues);
            log.log(INFO, "The issues report is generated in " + reportFile);
        } catch (Exception e) {
            log.log(SEVERE, "Unable to write json report.", e);
        }
    }

    private File resolveJsonReportFile() {
        String name = properties.getReportName();
        String location = properties.getReportLocation();
        File reportDir = new File(location);
        reportDir.mkdirs();
        return new File(reportDir, name + ".json");
    }

}
