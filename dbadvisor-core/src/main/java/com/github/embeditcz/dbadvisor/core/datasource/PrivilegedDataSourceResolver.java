package com.github.embeditcz.dbadvisor.core.datasource;

public interface PrivilegedDataSourceResolver {
    PrivilegedDataSourceProvider resolve(String dataSourceName);
}
