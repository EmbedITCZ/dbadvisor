package com.github.embeditcz.dbadvisor.core.internal.datasource;

import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceProvider;
import com.github.embeditcz.dbadvisor.core.datasource.PrivilegedDataSourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class PrivilegedDataSourceResolverImpl implements PrivilegedDataSourceResolver {

    private final List<PrivilegedDataSourceProvider> providers;
    private final Map<String, DataSource> dataSources;
    private FallBackPrivilegedDataSourceProvider defaultProvider;

    PrivilegedDataSourceResolverImpl(@Autowired(required = false) List<PrivilegedDataSourceProvider> providers, Map<String, DataSource> dataSources) {
        this.providers = nonNull(providers) ? providers : Collections.emptyList();
        this.dataSources = dataSources;
        this.defaultProvider = getDefault();
    }

    private FallBackPrivilegedDataSourceProvider getDefault() {
        if (isNull(defaultProvider)) {
            defaultProvider = new FallBackPrivilegedDataSourceProvider(dataSources);
        }
        return defaultProvider;
    }

    @Override
    public PrivilegedDataSourceProvider resolve(String dataSourceName) {
        return providers.stream()
            .filter(e -> e.getDataSourceName().equals(dataSourceName))
            .findFirst()
            .orElse(defaultProvider);
    }
}
