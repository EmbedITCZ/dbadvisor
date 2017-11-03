package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import java.util.Map;

import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractExecutionPlanAnalyzer;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class CpuCostAnalyzer extends AbstractExecutionPlanAnalyzer {

    private final ConversionService conversionService = new DefaultConversionService();
    private CpuCostProperties properties;

    public CpuCostAnalyzer(CpuCostProperties properties) {
        super(properties::isEnabled);
        this.properties = properties;
    }

    @Override
    protected String getName() {
        return "CPU Cost";
    }

    @Override
    protected void analyzeImpl(ExecutionPlanContext ctx) {
        Map<String, Object> firstLine = ctx.getPlanData().get(0);
        long cost = conversionService.convert(firstLine.get("CPU_COST"), Long.class);
        if (cost >= properties.getThreshold()) {
            Issue issue = issueBuilder.builder()
                    .type(getName())
                    .query(ctx.getQueryContext())
                    .weight(cost)
                    .description("CPU costs for query are over the limit")
                    .build();
            issueRepository.addIssue(issue);
        }
    }
}
