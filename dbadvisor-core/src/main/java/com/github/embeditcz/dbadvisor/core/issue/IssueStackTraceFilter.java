package com.github.embeditcz.dbadvisor.core.issue;

/**
 * Interface represents strategy for custom stack trace filtering,
 * all beans implementing this interface are automatically used.
 */
public interface IssueStackTraceFilter {

    String[] filterStackTrace(String[] stackTrace);

}
