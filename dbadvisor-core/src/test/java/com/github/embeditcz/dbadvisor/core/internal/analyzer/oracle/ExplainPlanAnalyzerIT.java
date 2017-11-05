package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ExplainPlanAnalyzerIT extends AbstractIT {

    @MockBean
    private CostBaseProperties costBaseProperties;

    @MockBean
    private CpuCostProperties cpuCostProperties;

    @MockBean
    private IoCostProperties ioCostProperties;

    @MockBean
    private FullAccessProperties fullAccessProperties;

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
        verifyIssue(issue, "Cost", "select 1 from dual", 0);
    }

    @Test
    public void shouldRunCpuBaseAnalyzer() {
        given(cpuCostProperties.getThreshold()).willReturn(-1L);
        given(cpuCostProperties.isEnabled()).willReturn(true);
        jdbcTemplate.execute("select 2 from dual");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        verifyIssue(issue, "CPU Cost", "select 2 from dual", 0);
    }


    @Test
    public void shouldRunIoCostAnalyzer() {
        given(ioCostProperties.getThreshold()).willReturn(-1L);
        given(ioCostProperties.isEnabled()).willReturn(true);
        jdbcTemplate.execute("select 3 from dual");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        verifyIssue(issue, "IO Cost", "select 3 from dual", 0);
    }


    @Test
    public void shouldRunFullAccessAnalyzer() {
        given(fullAccessProperties.getThreshold()).willReturn(1L);
        given(fullAccessProperties.isEnabled()).willReturn(true);
        jdbcTemplate.queryForList("select * from product p join product_type pt on pt.product_type_cd = p.product_type_cd where pt.product_type_cd = ?", "test");

        List<Issue> issues = issueRepository.getIssues();
        assertThat(issues).hasSize(1);

        Issue issue = issues.get(0);
        verifyIssue(issue, "Full Access", "select * from product p join product_type pt on pt.product_type_cd = p.product_type_cd where pt.product_type_cd = ?", 0);
    }

    private void verifyIssue(Issue issue, String type, String query, long weight) {
        assertThat(issue.getType()).isEqualTo(type);
        assertThat(issue.getQuery()).isEqualTo(query);
        assertThat(issue.getWeight()).isGreaterThan(weight);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotNull();
        assertThat(issue.getDescription()).isNotEmpty();
    }
}
