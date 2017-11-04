package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractExecutionPlanAnalyzer;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class FullAccessAnalyzer extends AbstractExecutionPlanAnalyzer {

    private final ConversionService conversionService = new DefaultConversionService();
    private FullAccessProperties properties;

    private static List<String> operations = Arrays.asList("INDEX", "MAT_VIEW REWRITE ACCESS", "TABLE ACCESS");
    private static List<String> options = Arrays.asList("FULL SCAN", "FULL SCAN DESCENDING", "FULL");


    public FullAccessAnalyzer(FullAccessProperties properties) {
        super(properties::isEnabled);
        this.properties = properties;
    }

    @Override
    protected String getName() {
        return "Full Access";
    }

    @Override
    protected void analyzeImpl(ExecutionPlanContext ctx) {
        List<String> objects = new ArrayList<>();
        ctx.getPlanData().forEach(i -> {
            String operation = conversionService.convert(i.get("OPERATION"), String.class);
            String option = conversionService.convert(i.get("OPTIONS"), String.class);
            String objectName = conversionService.convert(i.get("OBJECT_NAME"), String.class);
            if (operations.contains(operation) && options.contains(option)) {
                objects.add(objectName);
            }
        });

        if (objects.size() >= properties.getThreshold()) {
            Issue issue = issueBuilder.builder()
                    .type(getName())
                    .query(ctx.getQueryContext())
                    .weight(objects.size())
                    .description(format("These objects are accessed by full access: %s", objects))
                    .metadata("elapsedTime", ctx.getQueryContext().getExecInfo().getElapsedTime())
                    .build();
            issueRepository.addIssue(issue);
        }
    }
}
