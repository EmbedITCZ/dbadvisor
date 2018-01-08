package com.github.embeditcz.dbadvisor.core.analyzer;

/**
 * Interface represents database analyzer, which is executed once for every datasource.
 */
public interface DatabaseAnalyzer {

    void analyze(DatabaseContext ctx);

}
