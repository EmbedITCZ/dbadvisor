package com.github.embeditcz.dbadvisor.core.datasource;

import javax.sql.DataSource;
import java.util.List;

public interface PrivilegedDataSourceProvider {

    String getDataSourceName();

    DataSource getPrivilegedDataSource();

    List<String> getSchemas();
}
