package com.github.embeditcz.dbadvisor.core.internal.issue;

import com.github.embeditcz.dbadvisor.core.internal.DbAdvisorProperties;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
class IssueRepositoryImpl implements IssueRepository {

    private static final Comparator<Issue> ISSUE_COMPARATOR =
        Comparator.comparing(Issue::getType)
            .thenComparing(Comparator.comparing(Issue::getWeight).reversed())
            .thenComparing(Issue::getDataSourceName, Comparator.nullsLast(String::compareTo))
            .thenComparing(Issue::getQuery, Comparator.nullsLast(String::compareTo));

    private final SortedMap<String, SortedSet<Issue>> issuesByType = new TreeMap<>();

    private final DbAdvisorProperties properties;

    @Override
    public synchronized void addIssue(Issue issue) {
        SortedSet<Issue> issues = issuesByType.computeIfAbsent(issue.getType(), (k) -> new TreeSet<>(ISSUE_COMPARATOR));

        Issue similarIssue = findIssueWithIdenticalQueryAndDataSourceName(issue, issues);
        if (similarIssue != null) {
            if (similarIssue.getWeight() < issue.getWeight()) {
                issues.remove(similarIssue);
                issues.add(issue);
            }
        } else {
            issues.add(issue);
            if (issues.size() > properties.getMaxIssuesCountPerType()) {
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

    private Issue findIssueWithIdenticalQueryAndDataSourceName(Issue issue, SortedSet<Issue> issues) {
        Issue result = null;
        if (issue.getQuery() != null) {
            result = issues.stream()
                .filter(i -> Objects.equals(i.getQuery(), issue.getQuery()) &&
                    Objects.equals(i.getDataSourceName(), issue.getDataSourceName()))
                .findFirst()
                .orElse(null);
        }
        return result;
    }

}
