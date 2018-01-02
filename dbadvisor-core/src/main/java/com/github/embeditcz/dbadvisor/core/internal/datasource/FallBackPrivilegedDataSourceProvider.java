package com.github.embeditcz.dbadvisor.core.internal.datasource;

import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceProvider;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.isTrue;

public class FallBackPrivilegedDataSourceProvider implements PrivilegedDataSourceProvider {

    private final String dataSourceName;
    private final DataSource dataSource;
    private final String schemaName;

    FallBackPrivilegedDataSourceProvider(Map<String, DataSource> dataSources) {
        isTrue(dataSources.keySet().size() == 1, "Only one datasource is expected");
        this.dataSourceName = dataSources.keySet().iterator().next();
        this.dataSource = dataSources.get(dataSourceName);
        this.schemaName = resolveSchemaName(dataSource);
    }

    @Override
    public String getDataSourceName() {
        return dataSourceName;
    }

    @Override
    public DataSource getPrivilegedDataSource() {
        return dataSource;
    }

    @Override
    public List<String> getSchemas() {
        return Collections.singletonList(schemaName);
    }

    private String resolveSchemaName(DataSource dataSource) {
        try {
            return dataSource.getConnection().getMetaData().getUserName().toUpperCase();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot get schema name", e);
        }
    }
}
