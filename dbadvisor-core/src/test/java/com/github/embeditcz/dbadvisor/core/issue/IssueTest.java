package com.github.embeditcz.dbadvisor.core.issue;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.embeditcz.dbadvisor.core.internal.issue.IssueObjectMapper;
import org.junit.Test;

public class IssueTest {

    private IssueObjectMapper issueObjectMapper = new IssueObjectMapper();

    @Test
    public void shouldBeSerializableToJson() throws JsonProcessingException {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("elapsedTime", 1024);
        metadata.put("hcitrace", "123456789");

        Issue issue = new Issue(
                "SLOW QUERY",
                "select * from internet",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit",
                "dataSource",
                123,
                "ms",
                LocalDateTime.now(),
                Stream.of(Thread.currentThread().getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new),
                metadata);

        String json = issueObjectMapper.getObjectMapper().writeValueAsString(issue);

        assertThat(json).isNotEmpty();
    }

}
