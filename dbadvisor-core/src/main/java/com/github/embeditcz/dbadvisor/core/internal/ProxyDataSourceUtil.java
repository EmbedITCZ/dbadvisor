package com.github.embeditcz.dbadvisor.core.internal;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.springframework.util.ReflectionUtils;

public class ProxyDataSourceUtil {

    public static DataSource unwrap(DataSource dataSource) {
        if (dataSource instanceof ProxyDataSource) {
            Field dataSourceField = ReflectionUtils.findField(ProxyDataSource.class, "dataSource");
            dataSourceField.setAccessible(true);
            return (DataSource) ReflectionUtils.getField(dataSourceField, dataSource);
        }
        return dataSource;
    }

}
