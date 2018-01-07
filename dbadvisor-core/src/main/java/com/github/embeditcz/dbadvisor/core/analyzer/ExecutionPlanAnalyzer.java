package com.github.embeditcz.dbadvisor.core.analyzer;

/**
 * Interface represents execution plan analyzer, which is executed once for every query.
 */
public interface ExecutionPlanAnalyzer {

    void analyze(ExecutionPlanContext ctx);

}
