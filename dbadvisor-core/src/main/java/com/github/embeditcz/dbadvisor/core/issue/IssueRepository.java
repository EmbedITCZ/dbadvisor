package com.github.embeditcz.dbadvisor.core.issue;

import java.util.List;

/**
 * Interface represents repository which holds issues found during analysis.
 */
public interface IssueRepository {

    /**
     * Add issue to repository.
     * @param issue
     */
    void addIssue(Issue issue);

    /**
     * Get all issues currently holden by repository.
     * @return
     */
    List<Issue> getIssues();

    /**
     * Remove all issues from repository.
     */
    void clear();

}
