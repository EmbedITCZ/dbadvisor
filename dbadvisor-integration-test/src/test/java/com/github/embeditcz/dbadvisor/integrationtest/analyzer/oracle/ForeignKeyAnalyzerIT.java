package com.github.embeditcz.dbadvisor.integrationtest.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.internal.DBAdvisorDatabaseAnalyzerExecutor;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.ForeignKeyProperties;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ForeignKeyAnalyzerIT extends AbstractIT {

    @MockBean
    private ForeignKeyProperties foreignKeyProperties;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DBAdvisorDatabaseAnalyzerExecutor databaseAnalyzerExecutor;

    @Before
    public void init() {
        issueRepository.clear();
    }

    @Test
    public void shouldForeignKeyAnalyzer() {
        given(foreignKeyProperties.isEnabled()).willReturn(true);
        given(foreignKeyProperties.getThreshold()).willReturn(1L);

        databaseAnalyzerExecutor.afterPropertiesSet();

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        assertThat(issue.getType()).isEqualTo("Foreign key index");
        assertThat(issue.getQuery()).isNotEmpty();
        assertThat(issue.getWeight()).isGreaterThanOrEqualTo(0);
        assertThat(issue.getWeightUnit()).isEqualTo("idx");
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotNull();
        assertThat(issue.getDescription()).contains("On table: ACCOUNT with columns: OPEN_BRANCH_ID missing index for fk: ACCOUNT_BRANCH_FK");
    }
}
