package com.github.embeditcz.dbadvisor.core.internal.issue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.springframework.stereotype.Component;

@Component
class IssueRepositoryImpl implements IssueRepository {

    private static final int MAX_ISSUES_PER_TYPE = 100;

    private Map<String, SortedSet<Issue>> issuesByType = new TreeMap<>();
    private Comparator<Issue> issueComparator = (o1, o2) -> o2.getSortingKey().compareTo(o1.getSortingKey());

    @Override
    public synchronized void addIssue(Issue issue) {
        String type = issue.getType();
        SortedSet<Issue> issues = issuesByType.get(type);
        if (issues == null) {
            issues = new TreeSet<>(issueComparator);
            issuesByType.put(type, issues);
        }

        Issue issueWithSameQuery = issues.stream()
                .filter(i -> i.getQuery().equals(issue.getQuery()))
                .findFirst()
                .orElse(null);

        if (issueWithSameQuery != null) {
            long weight2 = issueWithSameQuery.getWeight();
            long weight1 = issue.getWeight();
            if (weight1 < weight2) {
                // we already have more serious issue for this query
                return;
            } else {
                issues.remove(issueWithSameQuery);
                issues.add(issue);
            }
        } else {
            issues.add(issue);
        }

        if (issues.size() > MAX_ISSUES_PER_TYPE) {
            Issue last = issues.last();
            issues.remove(last);
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

}
