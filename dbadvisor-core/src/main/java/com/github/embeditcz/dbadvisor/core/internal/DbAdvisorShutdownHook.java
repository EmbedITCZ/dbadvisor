package com.github.embeditcz.dbadvisor.core.internal;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.github.embeditcz.dbadvisor.core.internal.issue.IssueObjectMapper;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DbAdvisorShutdownHook {

    private static final Logger logger = Logger.getLogger("dbadvisor");

    private final IssueRepository issueRepository;
    private final IssueObjectMapper issueObjectMapper;

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    List<Issue> issues = issueRepository.getIssues();
                    File reportFile = new File(System.getProperty("java.io.tmpdir"), "dbadvisor.json");
                    issueObjectMapper.getObjectMapper().writeValue(reportFile, issues);
                    logger.log(INFO, "The issues report is generated in " + reportFile);
                } catch (Exception e) {
                    logger.log(SEVERE, "Unable to write json report.", e);
                }
            }
        });
    }

}
