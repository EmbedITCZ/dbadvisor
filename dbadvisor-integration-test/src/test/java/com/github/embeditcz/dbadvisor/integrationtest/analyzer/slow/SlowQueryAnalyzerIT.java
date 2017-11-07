package com.github.embeditcz.dbadvisor.integrationtest.analyzer.slow;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.slow.SlowQueryProperties;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class SlowQueryAnalyzerIT extends AbstractIT {

    @MockBean
    private SlowQueryProperties slowQueryProperties;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IssueRepository issueRepository;

    @Before
    public void init() {
        issueRepository.clear();
    }

    @Test
    public void shouldDetectSlowQuery() {
        given(slowQueryProperties.isEnabled()).willReturn(true);
        given(slowQueryProperties.getThreshold()).willReturn(-1L);

        jdbcTemplate.execute("select 1 from dual");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        assertThat(issue.getType()).isEqualTo("Slow query");
        assertThat(issue.getQuery()).isEqualTo("select 1 from dual");
        assertThat(issue.getWeight()).isGreaterThanOrEqualTo(0);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotNull();
        assertThat(issue.getDescription()).containsPattern("The query execution time (\\d)ms is larger than threshold.");
        assertThat(issue.getMetadata()).containsKey("elapsedTime");
    }

    @Test
    public void shouldNotDetectSlowQuery() {
        given(slowQueryProperties.isEnabled()).willReturn(true);
        given(slowQueryProperties.getThreshold()).willReturn(MAX_VALUE);

        jdbcTemplate.execute("select 1 from dual");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).isEmpty();
    }

}
