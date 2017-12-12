package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseAnalyzer;
import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseContext;
import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceProvider;
import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceResolver;
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
    private final PrivilegedDataSourceResolver privilegedDataSourceResolver;
    private final List<DatabaseAnalyzer> analyzers;

    @Override
    public void afterPropertiesSet() {
        log.log(Level.FINE, "Running database analyzers");
        dataSources.forEach((dataSourceName, dataSource) -> {
            PrivilegedDataSourceProvider provider = privilegedDataSourceResolver.resolve(dataSourceName);
            provider.getSchemas().forEach( schemaName ->
                analyzers.forEach( analyzer -> {
                    try {
                        log.log(Level.FINER, format("Running analyzer: %s on datasource: %s", analyzer.getClass().getSimpleName(), dataSourceName));
                        analyzer.analyze(new DatabaseContext(provider.getDataSourceName(), provider.getPrivilegedDataSource(), schemaName));
                    } catch (Exception e) {
                        log.log(Level.WARNING, "Analyzer failed", e);
                    }
                })
            );
        });
    }
}
