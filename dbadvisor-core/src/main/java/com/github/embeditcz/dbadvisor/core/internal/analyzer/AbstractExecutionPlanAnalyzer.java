package com.github.embeditcz.dbadvisor.core.internal.analyzer;

import java.util.function.Supplier;

import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.ExecutionPlanContext;
import com.github.embeditcz.dbadvisor.core.issue.IssueBuilder;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractExecutionPlanAnalyzer implements ExecutionPlanAnalyzer {

    @Autowired
    protected IssueBuilder issueBuilder;
    @Autowired
    protected IssueRepository issueRepository;

    private final Supplier<Boolean> enabledGetter;

    public AbstractExecutionPlanAnalyzer(Supplier<Boolean> enabledGetter) {
        this.enabledGetter = enabledGetter;
    }

    @Override
    public final void analyze(ExecutionPlanContext ctx) {
        if (isDisabled()) {
            return;
        }
        analyzeImpl(ctx);
    }

    protected abstract void analyzeImpl(ExecutionPlanContext ctx);

    protected abstract String getName();

    private boolean isDisabled() {
        return !enabledGetter.get();
    }

}
