package com.github.embeditcz.dbadvisor.core.internal.issue;

import java.util.ArrayList;
import java.util.List;

import com.github.embeditcz.dbadvisor.core.issue.IssueStackTraceFilter;
import org.springframework.stereotype.Component;

@Component
class IssueStackTraceFilterImpl implements IssueStackTraceFilter {

    @Override
    public String[] filterStackTrace(String[] stackTrace) {
        List<String> newStackTrace = new ArrayList<>();

        for (String stackTraceLine : stackTrace) {
            if (newStackTrace.isEmpty() && filterStackTraceLine(stackTraceLine)) {
                continue;
            }
            newStackTrace.add(stackTraceLine);
        }

        return newStackTrace.toArray(new String[newStackTrace.size()]);
    }

    private boolean filterStackTraceLine(String stackTraceLine) {
        return stackTraceLine.startsWith("com.github.embeditcz.dbadvisor.") ||
                stackTraceLine.startsWith("net.ttddyy.dsproxy.") ||
                stackTraceLine.startsWith("java.lang.Thread.getStackTrace") ||
                stackTraceLine.startsWith("com.sun.proxy.");
    }

}
