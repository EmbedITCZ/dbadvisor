package com.github.embeditcz.dbadvisor.core.internal.analyzer;

import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseContext;
import com.github.embeditcz.dbadvisor.core.issue.IssueBuilder;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractDatabaseAnalyzer implements DatabaseAnalyzer {

    @Autowired
    protected IssueBuilder issueBuilder;

    @Autowired
    protected IssueRepository issueRepository;

    private final Supplier<Boolean> enabledGetter;

    @Override
    public final void analyze(DatabaseContext ctx) {
        if (isDisabled()) {
            return;
        }
        analyzeImpl(ctx);
    }

    protected abstract void analyzeImpl(DatabaseContext ctx);

    protected abstract String getName();

    private boolean isDisabled() {
        return !enabledGetter.get();
    }

}
