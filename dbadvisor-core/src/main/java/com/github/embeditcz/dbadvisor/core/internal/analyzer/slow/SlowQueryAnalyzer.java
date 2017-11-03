package com.github.embeditcz.dbadvisor.core.internal.analyzer.slow;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import net.ttddyy.dsproxy.QueryInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SlowQueryAnalyzer implements QueryAnalyzer {

    private final SlowQueryProperties properties;
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

            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("elapsedTime", ctx.getExecInfo().getElapsedTime());

            Issue issue = new Issue();
            issue.setTimestamp(LocalDateTime.now());
            issue.setType("SLOW_QUERY");
            issue.setQuery(resolveQuery(ctx));
            issue.setDescription("slow query " + issue.getQuery() + " time " + ctx.getExecInfo().getElapsedTime());
            issue.setWeight(ctx.getExecInfo().getElapsedTime());
            issue.setStackTrace(Stream.of(Thread.currentThread().getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new));
            issue.setMetadata(metadata);

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

    private String resolveQuery(QueryContext ctx) {
        String query = null;
        if (!ctx.getQueryInfoList().isEmpty()) {
            QueryInfo queryInfo = ctx.getQueryInfoList().get(0);
            query = queryInfo.getQuery();
        }
        return query;
    }

}
