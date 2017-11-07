package com.github.embeditcz.dbadvisor.core.internal.issue;

import java.util.stream.Stream;

import com.github.embeditcz.dbadvisor.core.issue.IssueStackTraceFilter;
import org.springframework.stereotype.Component;

@Component
class IssueStackTraceFilterImpl implements IssueStackTraceFilter {

    @Override
    public String[] filterStackTrace(String[] stackTrace) {
        String[] result = null;
        if (stackTrace != null) {
            result = Stream.of(stackTrace)
                    .filter(stl -> !(stl.contains("java.lang.Thread.getStackTrace") ||
                            stl.contains("com.github.embeditcz.dbadvisor.") ||
                            stl.contains("net.ttddyy.dsproxy.") ||
                            stl.contains("com.sun.proxy.")))
                    .toArray(String[]::new);
        }
        return result;
    }

}