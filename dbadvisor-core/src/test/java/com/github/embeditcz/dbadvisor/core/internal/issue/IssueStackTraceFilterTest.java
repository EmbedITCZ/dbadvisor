package com.github.embeditcz.dbadvisor.core.internal.issue;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.embeditcz.dbadvisor.core.issue.IssueStackTraceFilter;
import org.junit.Test;

public class IssueStackTraceFilterTest {

    private IssueStackTraceFilter stackTraceFilter = new IssueStackTraceFilterImpl();

    @Test
    public void shouldFilterStackTrace() {
        String[] stackTrace = new String[]{
                "java.lang.Thread.getStackTrace(Thread.java:1552)",
                "com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle.ExplainPlanAnalyzer.analyzeImpl(ExplainPlanAnalyzer.java:47)",
                "com.github.embeditcz.dbadvisor.core.internal.DbAdvisorQueryExecutionListener.analyzeQuery(DbAdvisorQueryExecutionListener.java:53)",
                "net.ttddyy.dsproxy.listener.ChainListener.afterQuery(ChainListener.java:27)",
                "net.ttddyy.dsproxy.proxy.jdk.PreparedStatementInvocationHandler.invoke(PreparedStatementInvocationHandler.java:35)",
                "com.sun.proxy.$Proxy273.executeQuery(Unknown Source)",
                "org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:70)",
                "org.hibernate.loader.Loader.getResultSet(Loader.java:2117)"
        };

        String[] result = stackTraceFilter.filterStackTrace(stackTrace);

        assertThat(result)
                .hasSize(2)
                .containsOnly(stackTrace[6], stackTrace[7]);
    }

    @Test
    public void shouldBeNullSafe() {
        String[] result = stackTraceFilter.filterStackTrace(null);
        assertThat(result).isNull();
    }

}
