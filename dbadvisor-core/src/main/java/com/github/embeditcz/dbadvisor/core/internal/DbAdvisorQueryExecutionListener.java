package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
class DbAdvisorQueryExecutionListener implements QueryExecutionListener {

    private final ApplicationContext applicationContext;
    private List<QueryAnalyzer> analyzers = null;

    // TODO analyzers have to be initialized lazily
    private synchronized List<QueryAnalyzer> getAnalyzers() {
        if (analyzers == null) {
            Map<String, QueryAnalyzer> map = applicationContext.getBeansOfType(QueryAnalyzer.class);
            analyzers = new ArrayList<>(map.values());
        }
        return analyzers;
    }

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        // noop
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        QueryContext ctx = new QueryContext(execInfo, queryInfoList);
        String query = ctx.resolveQuery();
        if (query != null && query.startsWith("select ")) {
            analyzeQuery(ctx);
        }
    }

    private void analyzeQuery(final QueryContext ctx) {
        for (QueryAnalyzer analyzer : getAnalyzers()) {
            try {
                analyzer.analyze(ctx);
            } catch (Exception e) {
                log.log(Level.WARNING, "Analyzer failed", e);
            }
        }
    }

}
