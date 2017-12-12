package com.github.embeditcz.dbadvisor.core.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@Getter
@RequiredArgsConstructor
public class DatabaseContext {
    private final String dataSourceName;
    private final DataSource dataSource;
    private final String schemaName;
}
