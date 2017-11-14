package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.lang.String.format;

@Log
@Component
@RequiredArgsConstructor
public class DBAdvisorDatabaseAnalyzerExecutor implements InitializingBean {

    private final Map<String, DataSource> dataSources;
    private final List<DatabaseAnalyzer> analyzers;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.log(Level.FINE, "Running database analyzers");
        dataSources.forEach((dataSourceName, dataSource) -> {
            analyzers.forEach( analyzer -> {
                try {
                    log.log(Level.FINER, format("Running analyzer: %s on datasource: %s", analyzer.getClass().getSimpleName(), dataSourceName));
                    analyzer.analyze(new DatabaseContext(dataSourceName, dataSource));
                } catch (Exception e) {
                    log.log(Level.WARNING, "Analyzer failed", e);
                }
            });
        });
    }
}
