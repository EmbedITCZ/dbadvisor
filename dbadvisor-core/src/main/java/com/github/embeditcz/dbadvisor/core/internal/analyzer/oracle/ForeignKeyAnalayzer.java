package com.github.embeditcz.dbadvisor.core.internal.analyzer.oracle;

import com.github.embeditcz.dbadvisor.core.analyzer.DatabaseContext;
import com.github.embeditcz.dbadvisor.core.internal.analyzer.AbstractDatabaseAnalyzer;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Component
public class ForeignKeyAnalayzer extends AbstractDatabaseAnalyzer {

    private static final String FOREIGN_KEY_QUERY = "WITH constr AS (" +
        "SELECT c.owner, " +
            "c.table_name, " +
            "c.constraint_name, " +
            "listagg(col.column_name, ', ') within group (ORDER BY col.column_name) column_list_alphabetic, " +
            "listagg(col.column_name, ', ') within group (ORDER BY col.position) column_list " +
        "FROM all_constraints c " +
        "JOIN all_cons_columns col ON ( col.owner = c.owner AND col.constraint_name = c.constraint_name AND col.table_name = c.table_name ) " +
        "WHERE c.constraint_type = 'R' " +
            "AND c.owner = ? " +
        "GROUP BY c.owner, c.table_name, c.constraint_name), " +
        "idx AS (" +
        "SELECT table_owner, table_name, " +
            "listagg(column_name, ', ') within group (ORDER BY column_name) column_list_alphabetic, " +
            "listagg(column_name, ', ') within group (ORDER BY column_position) column_list " +
        "FROM all_ind_columns " +
        "WHERE table_owner = ? " +
        "GROUP BY table_owner, table_name, index_owner, index_name) " +
        "SELECT constr.owner, constr.table_name, constr.constraint_name, constr.column_list " +
        "FROM constr " +
        "WHERE NOT EXISTS ( " +
            "SELECT 1 " +
            "FROM idx " +
            "WHERE constr.owner = idx.table_owner " +
                "AND constr.table_name = idx.table_name " +
                "AND ( idx.column_list_alphabetic = constr.column_list_alphabetic " +
                    "OR instr(idx.column_list,constr.column_list) = 1 ))";

    private final ForeignKeyProperties properties;

    ForeignKeyAnalayzer(ForeignKeyProperties properties) {
        super(properties::isEnabled);
        this.properties = properties;
    }

    @Override
    protected String getName() {
        return "Foreign key index";
    }

    @Override
    protected void analyzeImpl(DatabaseContext ctx) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ctx.getDataSource());
        String schemaName = resolveSchemaName(ctx.getDataSource());
        List<Map<String, Object>> foreignKeys = jdbcTemplate.queryForList(FOREIGN_KEY_QUERY, schemaName, schemaName);
        if (foreignKeys.size() > 0) {
            Issue issue = issueBuilder.builder()
                .type(getName())
                .weight(foreignKeys.size())
                .query(FOREIGN_KEY_QUERY)
                .weightUnit("idx")
                .description(buildDescription(foreignKeys))
                .build();
            issueRepository.addIssue(issue);
        }
    }

    private String buildDescription(List<Map<String, Object>> foreignKeys) {
        final StringBuilder description = new StringBuilder("List of missing indexes for foreign keys: \n");
        foreignKeys.forEach( i -> {
                description.append(format("On table: %s with columns: %s missing index for fk: %s \n"
                    , i.get("TABLE_NAME")
                    , i.get("COLUMN_LIST")
                    , i.get("CONSTRAINT_NAME")));
            }
        );
        return description.toString();
    }

    private String resolveSchemaName(DataSource dataSource) {
        try {
            return dataSource.getConnection().getMetaData().getUserName().toUpperCase();
        } catch (SQLException e) {
            throw new IllegalStateException("Can not get schema name", e);
        }
    }
}
