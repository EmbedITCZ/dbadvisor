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

}
