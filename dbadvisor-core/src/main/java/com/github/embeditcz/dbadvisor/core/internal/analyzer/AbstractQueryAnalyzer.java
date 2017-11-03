package com.github.embeditcz.dbadvisor.core.internal.analyzer;

import java.util.function.Supplier;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.IssueBuilder;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractQueryAnalyzer implements QueryAnalyzer {

    @Autowired
    protected IssueBuilder issueBuilder;
    @Autowired
    protected IssueRepository issueRepository;

    private final Supplier<Boolean> enabledGetter;
    private final Supplier<Boolean> ignoreBatchGetter;

    public AbstractQueryAnalyzer(Supplier<Boolean> enabledGetter, Supplier<Boolean> ignoreBatchGetter) {
        this.enabledGetter = enabledGetter;
        this.ignoreBatchGetter = ignoreBatchGetter;
    }

    @Override
    public final void analyze(QueryContext ctx) {
        if (isDisabled()) {
            return;
        }
        if (isIgnoreBatch(ctx)) {
            return;
        }
        analyzeImpl(ctx);
    }

    protected abstract void analyzeImpl(QueryContext ctx);

    private boolean isDisabled() {
        return !enabledGetter.get();
    }

    private boolean isIgnoreBatch(QueryContext ctx) {
        return ignoreBatchGetter.get() && ctx.getExecInfo().isBatch();
    }

}
