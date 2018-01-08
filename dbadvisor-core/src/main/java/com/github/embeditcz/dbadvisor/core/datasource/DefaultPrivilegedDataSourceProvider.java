package com.github.embeditcz.dbadvisor.core.datasource;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.util.List;

/**
 * This class is simple implementation of {@link PrivilegedDataSourceProvider}
 * for convenient usage in configuration.
 *
 * <pre>{@code
 * @literal @Bean
 * PrivilegedDataSourceProvider privilegedBankDataSource() throws SQLException {
 *     OracleDataSource dataSource = new OracleDataSource();
 *     dataSource.setUser("system");
 *     dataSource.setPassword("secret");
 *     dataSource.setURL("...");
 *
 *     return DefaultPrivilegedDataSourceProvider.builder()
 *         .dataSourceName("dataSource")
 *         .privilegedDataSource(dataSource)
 *         .schemas(Arrays.asList("BANK"))
 *         .build();
 * }
 * }</pre>
 */
@Getter
@Builder
@RequiredArgsConstructor
public class DefaultPrivilegedDataSourceProvider implements PrivilegedDataSourceProvider {

    private final String dataSourceName;

    private final DataSource privilegedDataSource;

    private final List<String> schemas;

}
