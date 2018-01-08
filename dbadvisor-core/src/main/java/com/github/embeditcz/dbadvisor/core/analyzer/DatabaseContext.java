package com.github.embeditcz.dbadvisor.core.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

/**
 * This class holds data for database analysis, see {@link DatabaseAnalyzer}.
 */
@Getter
@RequiredArgsConstructor
public class DatabaseContext {

    private final String dataSourceName;

    private final DataSource dataSource;

    private final String schemaName;

}
