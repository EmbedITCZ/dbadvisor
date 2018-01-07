package com.github.embeditcz.dbadvisor.core.datasource;

import javax.sql.DataSource;
import java.util.List;

/**
 * Interface represents provider of so called "privileged data source",
 * which is used by some analyzers, performing operations, on which standard
 * application data source, has not enough privileges.
 */
public interface PrivilegedDataSourceProvider {

    String getDataSourceName();

    DataSource getPrivilegedDataSource();

    List<String> getSchemas();

}
