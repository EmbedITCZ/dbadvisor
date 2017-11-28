package com.github.embeditcz.dbadvisor.integrationtest.issue;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueBuilder;
import com.github.embeditcz.dbadvisor.integrationtest.AbstractIT;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueBuilderIT extends AbstractIT {

    @Autowired
    private IssueBuilder issueBuilder;

    @Test
    public void shouldBuildIssue() {
        ExecutionInfo executionInfo = new ExecutionInfo();
        executionInfo.setDataSourceName("ds");
        QueryContext queryContext = new QueryContext(executionInfo, asList(new QueryInfo("q")));

        Issue issue = issueBuilder.builder()
            .type("t")
            .query(queryContext)
            .description("d")
            .weight(0)
            .metadata("m1", "v1")
            .build();

        assertThat(issue.getType()).isEqualTo("t");
        assertThat(issue.getQuery()).isEqualTo("q");
        assertThat(issue.getDataSourceName()).isEqualTo("ds");
        assertThat(issue.getDescription()).isEqualTo("d");
        assertThat(issue.getMetadata()).containsKey("m1");
        assertThat(issue.getWeight()).isEqualTo(0);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotEmpty();
    }

    @Test
    public void shouldFilterStackTrace() {
        Issue issue = issueBuilder.builder().build();

        String[] stackTrace = issue.getStackTrace();

        for (String stackTraceLine : stackTrace) {
            assertThat(stackTraceLine).doesNotStartWith("com.github.embeditcz.dbadvisor");
        }
    }

}
