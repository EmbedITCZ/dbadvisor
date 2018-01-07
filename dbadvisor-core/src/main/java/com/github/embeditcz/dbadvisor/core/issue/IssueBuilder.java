package com.github.embeditcz.dbadvisor.core.issue;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;

import java.time.LocalDateTime;

/**
 * Fluent API for building issue.
 *
 * <pre>{@code
 * Issue issue = issueBuilder.builder()
 *     .type("Slow query")
 *     .description("The query execution time 1024ms is larger than threshold.")
 *     .weight(1024)
 *     .metadata("query", "select * from ...")
 *     .build();
 * }</pre>
 */
public interface IssueBuilder {

    Builder builder();

    interface Builder {

        Builder type(String type);

        Builder description(String description);

        Builder timestamp(LocalDateTime timestamp);

        Builder query(QueryContext queryContext);

        Builder query(String query);

        Builder dataSourceName(String dataSourceName);

        Builder weight(long weight);

        Builder weightUnit(String weightUnit);

        Builder metadata(String name, Object value);

        Issue build();

    }

}
