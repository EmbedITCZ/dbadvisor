package com.github.embeditcz.dbadvisor.core.datasource;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class DefaultPrivilegedDataSourceProvider implements PrivilegedDataSourceProvider {

    private final String dataSourceName;
    private final DataSource privilegedDataSource;
    private final List<String> schemas;
}
