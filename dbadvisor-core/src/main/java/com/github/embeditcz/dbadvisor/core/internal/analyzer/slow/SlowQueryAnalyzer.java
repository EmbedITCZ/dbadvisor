package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import static java.text.MessageFormat.format;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractQueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import org.springframework.stereotype.Component;

@Component
class SlowQueryAnalyzer extends AbstractQueryAnalyzer {

    private final SlowQueryProperties properties;

    SlowQueryAnalyzer(SlowQueryProperties properties) {
        super(properties::isEnabled, properties::isIgnoreBatch);
        this.properties = properties;
    }

    @Override
    protected void analyzeImpl(QueryContext ctx) {
        if (isSlowQuery(ctx)) {
            Issue issue = issueBuilder.builder()
                    .type("Slow query")
                    .description(format("The query execution time {0}ms is larger than threshold.", ctx.getExecInfo().getElapsedTime()))
                    .query(ctx)
                    .weight(ctx.getExecInfo().getElapsedTime())
                    .metadata("elapsedTime", ctx.getExecInfo().getElapsedTime())
                    .build();

            issueRepository.addIssue(issue);
        }
    }

    private boolean isSlowQuery(QueryContext ctx) {
        return ctx.getExecInfo().getElapsedTime() > properties.getTimeThreshold();
    }

}
