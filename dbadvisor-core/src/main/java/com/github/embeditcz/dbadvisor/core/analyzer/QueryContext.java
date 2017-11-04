package com.github.embeditcz.dbadvisor.core.analyzer;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

@Getter
@RequiredArgsConstructor
public class QueryContext {

    private final ExecutionInfo execInfo;
    private final List<QueryInfo> queryInfoList;

    public String resolveQuery() {
        String query = null;
        if (!queryInfoList.isEmpty()) {
            QueryInfo queryInfo = queryInfoList.get(0);
            query = queryInfo.getQuery();
        }
        return query;
    }

}
