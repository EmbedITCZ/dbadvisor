package com.github.embeditcz.dbadvisor.core.analyzer;

/**
 * Interface represents query analyzer, which is executed every time, when query is executed.
 */
public interface QueryAnalyzer {

    void analyze(QueryContext ctx);

}
