package com.github.embeditcz.dbadvisor.integrationtest.analyzer.nplus1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import com.github.embeditcz.dbadvisor.core.internal.analyzer.nplus1.Nplus1QueryProperties;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class Nplus1QueryAnalyzerIT extends AbstractIT {

    @MockBean
    private Nplus1QueryProperties nplus1QueryProperties;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private IssueRepository issueRepository;

    @Before
    public void init() {
        issueRepository.clear();
    }

    @Test
    public void shouldDetectNplus1Issue() {
        given(nplus1QueryProperties.isEnabled()).willReturn(true);
        given(nplus1QueryProperties.getThreshold()).willReturn(3L);

        transactionTemplate.execute(status -> {
            jdbcTemplate.queryForMap("select id, name from (select 1 as id, 'foo' as name from dual) where id = ?", 1);
            jdbcTemplate.queryForMap("select id, name from (select 1 as id, 'foo' as name from dual) where id = ?", 1);
            jdbcTemplate.queryForMap("select id, name from (select 1 as id, 'foo' as name from dual) where id = ?", 1);
            return null;
        });

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        assertThat(issue.getType()).isEqualTo("N+1");
        assertThat(issue.getQuery()).isNotEmpty();
        assertThat(issue.getWeight()).isEqualTo(3);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotNull();
        assertThat(issue.getDescription()).isNotEmpty();
    }

}
