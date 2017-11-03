package com.github.embeditcz.dbadvisor.core.issue;

public interface IssueStackTraceFilter {

    boolean accept(String stackTraceLine);

}
