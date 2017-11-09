package com.github.embeditcz.dbadvisor.core.issue;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;

import java.time.LocalDateTime;

/**
 * Fluent API for building issue.
 *
 * <pre>{@code
 *       Issue issue = issueBuilder.builder()
 *          .type("t")
 *          .description("d")
 *          .weight(0)
 *          .metadata("m1", "v1")
 *          .build();
 * }</pre>
 */
public interface IssueBuilder {

    Builder builder();

    interface Builder {

        Builder type(String type);

        Builder description(String description);

        Builder timestamp(LocalDateTime timestamp);

        Builder query(QueryContext queryContext);

        Builder weight(long weight);

        Builder weightUnit(String weightUnit);

        Builder metadata(String name, Object value);

        Issue build();

    }

}
