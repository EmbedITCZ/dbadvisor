package com.github.embeditcz.dbadvisor.core.analyzer;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExecutionPlanContext {

    private final QueryContext queryContext;
    private final List<Map<String, Object>> planData;
}
