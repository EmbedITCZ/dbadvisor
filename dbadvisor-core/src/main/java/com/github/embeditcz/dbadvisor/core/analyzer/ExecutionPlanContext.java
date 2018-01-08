package com.github.embeditcz.dbadvisor.core.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * This class holds data for execution plan analysis, see {@link ExecutionPlanAnalyzer}.
 */
@Getter
@RequiredArgsConstructor
public class ExecutionPlanContext {

    private final QueryContext queryContext;

    private final List<Map<String, Object>> planData;

}
