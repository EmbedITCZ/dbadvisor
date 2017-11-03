package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class ExplainPlanAnalyzerIT extends AbstractIT {

    @MockBean
    private CostBaseProperties costBaseProperties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IssueRepository issueRepository;

    @Before
    public void init() {
        issueRepository.clear();
    }

    @Test
    public void shouldRunCostBaseAnalyzer() {
        given(costBaseProperties.getThreshold()).willReturn(-1L);
        given(costBaseProperties.isEnabled()).willReturn(true);
        jdbcTemplate.execute("select 1 from dual");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        assertThat(issue.getType()).isEqualTo("Cost");
        assertThat(issue.getQuery()).isEqualTo("select 1 from dual");
        assertThat(issue.getWeight()).isGreaterThan(0);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotNull();
        assertThat(issue.getDescription()).isNotEmpty();
    }
}
