package com.github.embeditcz.dbadvisor.core.issue;

import java.time.LocalDateTime;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;

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
