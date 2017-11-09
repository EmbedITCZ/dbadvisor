package com.github.embeditcz.dbadvisor.core.issue;

import java.util.List;

/**
 * Repository which holds issues produced via run.
 */
public interface IssueRepository {

    /**
     * Add issue to repository.
     * @param issue Issue which will be holden by repository
     */
    void addIssue(Issue issue);

    /**
     * Get all issues currently holden by repository.
     * @return List of issues
     */
    List<Issue> getIssues();

    /**
     * Remove all issues from repository.
     */
    void clear();

}
