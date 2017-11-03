package com.github.embeditcz.dbadvisor.core.issue;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IssueBuilderIT extends AbstractIT {

    @Autowired
    private IssueBuilder issueBuilder;

    @Test
    public void shouldBuildIssue() {
        Issue issue = issueBuilder.builder()
                .type("t")
                .description("d")
                .weight(0)
                .metadata("m1", "v1")
                .build();

        assertThat(issue.getType()).isEqualTo("t");
        assertThat(issue.getDescription()).isEqualTo("d");
        assertThat(issue.getMetadata()).containsKey("m1");
        assertThat(issue.getWeight()).isEqualTo(0);
        assertThat(issue.getTimestamp()).isNotNull();
        assertThat(issue.getStackTrace()).isNotEmpty();
    }

}
