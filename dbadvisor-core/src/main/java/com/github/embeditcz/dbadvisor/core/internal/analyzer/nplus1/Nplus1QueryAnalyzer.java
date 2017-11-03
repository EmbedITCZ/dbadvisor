package com.github.embeditcz.dbadvisor.core.internal.analyzer.nplus1;

import com.github.embeditcz.dbadvisor.core.analyzer.QueryAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.QueryContext;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class Nplus1QueryAnalyzer implements QueryAnalyzer {

    private final Nplus1QueryProperties properties;
    private final IssueRepository issueRepository;

    @Override
    public void analyze(QueryContext ctx) {

    }

}
