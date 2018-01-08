package com.github.embeditcz.dbadvisor.core.datasource;

/**
 * Interface represents resolution strategy of so called "privileged data sources",
 * see {@link PrivilegedDataSourceProvider}.
 */
public interface PrivilegedDataSourceResolver {

    PrivilegedDataSourceProvider resolve(String dataSourceName);

}
