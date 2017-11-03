package com.github.embeditcz.dbadvisor.core.issue;

import java.util.List;

public interface IssueRepository {

    void addIssue(Issue issue);

    List<Issue> getIssues();

    void clear();

}
