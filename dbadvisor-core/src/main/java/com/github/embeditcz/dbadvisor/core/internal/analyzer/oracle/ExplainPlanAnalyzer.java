package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static java.lang.String.format;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanContext;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractQueryAnalyzer;
import net.ttddyy.dsproxy.QueryInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExplainPlanAnalyzer extends AbstractQueryAnalyzer {

    public static final String EXPLAIN_PLAN_QUERY = "EXPLAIN PLAN SET STATEMENT_ID = '%s' FOR %s";
    public static final String GET_PLAN_QUERY = "SELECT * FROM PLAN_TABLE "
            + "CONNECT BY PRIOR id = parent_id AND PRIOR statement_id = statement_id "
            + "START WITH id = 0 AND statement_id = ? ORDER BY id";

    private final List<ExecutionPlanAnalyzer> analyzers;
    private final Map<String, DataSource> dataSources;

    private Set<String> processedQueries = ConcurrentHashMap.newKeySet();
    private AtomicInteger id = new AtomicInteger();

    ExplainPlanAnalyzer(ExplainPlanProperties properties, Map<String, DataSource> dataSources, List<ExecutionPlanAnalyzer> analyzers) {
        super(properties::isEnabled, properties::isIgnoreBatch);
        this.dataSources = dataSources;
        this.analyzers = analyzers;
    }

    @Override
    protected void analyzeImpl(QueryContext ctx) {
        String query = resolveQuery(ctx);
        if (!processedQueries.contains(query)) {
            DataSource dataSource = resolveDataSource(ctx.getExecInfo().getDataSourceName());
            List<Map<String, Object>> plan = prepareExplainPlan(dataSource, query);
            analyzers.forEach(it -> it.analyze(new ExecutionPlanContext(ctx, plan)));
            processedQueries.add(query);
        }
    }

    private List<Map<String, Object>> prepareExplainPlan(DataSource dataSource, String query) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String uniqueID = getUniqueID();
        jdbcTemplate.execute(format(EXPLAIN_PLAN_QUERY, uniqueID, query));
        return jdbcTemplate.queryForList(GET_PLAN_QUERY, uniqueID);
    }

    private String getUniqueID() {
        return "DB_ANAL_" + id.incrementAndGet();
    }

    private DataSource resolveDataSource(String dataSourceName) {
        try {
            return this.dataSources.get(dataSourceName).unwrap(DataSource.class);
        } catch (SQLException e) {
            throw new IllegalStateException("Not able to unwrap datasource", e);
        }
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
