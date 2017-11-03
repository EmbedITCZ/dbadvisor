package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueBuilder;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SlowQueryAnalyzer implements QueryAnalyzer {

    private final SlowQueryProperties properties;
    private final IssueBuilder issueBuilder;
    private final IssueRepository issueRepository;

    @Override
    public void analyze(QueryContext ctx) {
        if (isDisabled()) {
            return;
        }
        if (isIgnoreBatch(ctx)) {
            return;
        }
        if (isSlowQuery(ctx)) {
            Issue issue = issueBuilder.builder()
                    .type("SLOW_QUERY")
                    .description("slow query time " + ctx.getExecInfo().getElapsedTime())
                    .query(ctx)
                    .weight(ctx.getExecInfo().getElapsedTime())
                    .metadata("elapsedTime", ctx.getExecInfo().getElapsedTime())
                    .build();

            issueRepository.addIssue(issue);
        }
    }

    private boolean isDisabled() {
        return !properties.isEnabled();
    }

    private boolean isIgnoreBatch(QueryContext ctx) {
        return properties.isIgnoreBatch() && ctx.getExecInfo().isBatch();
    }

    private boolean isSlowQuery(QueryContext ctx) {
        return ctx.getExecInfo().getElapsedTime() > properties.getTimeThreshold();
    }

}
