package com.github.embeditcz.dbadvisor.core.internal.issue;

import java.util.ArrayList;
import java.util.List;

import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.springframework.stereotype.Component;

@Component
class IssueRepositoryImpl implements IssueRepository {

    private final List<Issue> issues = new ArrayList<>();

    @Override
    public void addIssue(Issue issue) {
        issues.add(issue);
    }

    @Override
    public List<Issue> getIssues() {
        return issues;
    }

    @Override
    public void clear() {
        issues.clear();
    }

}
