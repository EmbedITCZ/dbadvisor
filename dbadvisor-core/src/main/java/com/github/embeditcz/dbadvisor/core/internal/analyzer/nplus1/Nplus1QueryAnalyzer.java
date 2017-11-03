package com.github.embeditcz.dbadvisor.core.internal.analyzer.nplus1;

import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.Map;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractQueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import org.springframework.stereotype.Component;

@Component
class Nplus1QueryAnalyzer extends AbstractQueryAnalyzer {

    private final ThreadLocal<Counter> counterThreadLocal = ThreadLocal.withInitial(Counter::new);
    private final Nplus1QueryProperties properties;

    Nplus1QueryAnalyzer(Nplus1QueryProperties properties) {
        super(properties::isEnabled, TRUE::booleanValue);
        this.properties = properties;
    }

    @Override
    protected void analyzeImpl(QueryContext ctx) {
        if (ctx.getQueryInfoList().size() == 1) {
            long connectionId = ctx.getExecInfo().getConnectionId();
            String query = ctx.getQueryInfoList().get(0).getQuery();

            Counter counter = counterThreadLocal.get();
            if (counter.connectionId != connectionId) {
                counter.connectionId = connectionId;
                counter.counterMap.clear();
            }

            long count = counter.incrementAndGet(query);

            if (count > properties.getThreshold()) {
                Issue issue = issueBuilder.builder()
                        .type("N+1")
                        .description("N+1 query issue found")
                        .query(ctx)
                        .weight(count)
                        .weightUnit("x")
                        .build();
                issueRepository.addIssue(issue);
            }
        }
    }

    private class Counter {

        private long connectionId = -1;
        private Map<String, Long> counterMap = new HashMap<>();

        public long incrementAndGet(String query) {
            Long counter = counterMap.get(query);
            if (counter == null) {
                counter = 1L;
            } else {
                counter = counter + 1L;
            }
            counterMap.put(query, counter);
            return counter;
        }

    }

}
