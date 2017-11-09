package com.github.embeditcz.dbadvisor.core.internal.issue;

import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class IssueRepositoryImpl implements IssueRepository {

    private static final int MAX_ISSUES_PER_TYPE = 100;

    private static final Comparator<Issue> ISSUE_COMPARATOR =
        Comparator.comparing(Issue::getType)
            .thenComparing(Comparator.comparing(Issue::getWeight).reversed())
            .thenComparing(Issue::getQuery);


    private final SortedMap<String, SortedSet<Issue>> issuesByType = new TreeMap<>();

    @Override
    public synchronized void addIssue(Issue issue) {
        SortedSet<Issue> issues = issuesByType.computeIfAbsent(issue.getType(), (k) -> new TreeSet<>(ISSUE_COMPARATOR));

        Issue issueWithIdenticalQuery = findIssueWithIdenticalQuery(issue, issues);
        if (issueWithIdenticalQuery != null) {
            if (issueWithIdenticalQuery.getWeight() < issue.getWeight()) {
                issues.remove(issueWithIdenticalQuery);
                issues.add(issue);
            }
        } else {
            issues.add(issue);
            if (issues.size() > MAX_ISSUES_PER_TYPE) {
                issues.remove(issues.last());
            }
        }
    }

    @Override
    public synchronized List<Issue> getIssues() {
        List<Issue> result = new ArrayList<>();
        for (Map.Entry<String, SortedSet<Issue>> issues : issuesByType.entrySet()) {
            result.addAll(issues.getValue());
        }
        return result;
    }

    @Override
    public synchronized void clear() {
        issuesByType.clear();
    }

    private Issue findIssueWithIdenticalQuery(Issue issue, SortedSet<Issue> issues) {
        return issues.stream()
            .filter(i -> i.getQuery().equals(issue.getQuery()))
            .findFirst()
            .orElse(null);
    }

}
