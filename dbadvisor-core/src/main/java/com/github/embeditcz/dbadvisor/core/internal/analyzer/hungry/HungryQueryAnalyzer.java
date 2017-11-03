package com.github.embeditcz.dbadvisor.core.internal.analyzer.hungry;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class HungryQueryAnalyzer implements QueryAnalyzer {

    private final HungryQueryProperties properties;
    private final IssueRepository issueRepository;

    @Override
    public void analyze(QueryContext ctx) {

    }

}
