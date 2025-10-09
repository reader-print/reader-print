package com.readerprint.backend.common.config;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Set;

@Configuration
public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {

    @Override
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
                                String sql, String url) {
        // sql 이 없다면 출력하지 않아도 됨
        if (sql.trim().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(category).append(" ").append(elapsed).append("ms");

        if (StringUtils.hasText(sql)) {
            String formattedSql = format(sql);

            if (!"prod".equals(System.getProperty("spring.profiles.active"))) {
                formattedSql = highlight(formattedSql);
            }
            sb.append(formattedSql);
        }
        return sb.toString();
    }

    private String format(String sql) {
        if (isDDL(sql)) {
            return FormatStyle.DDL.getFormatter().format(sql);
        } else if (isBasic(sql)) {
            return FormatStyle.BASIC.getFormatter().format(sql);
        }
        return sql;
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private boolean isDDL(String sql) {
        return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
    }

    private boolean isBasic(String sql) {
        Set<String> validCommands = Set.of("select", "insert", "update", "delete");
        String lowerCaseSql = sql.trim().toLowerCase();

        return validCommands.stream().anyMatch(lowerCaseSql::startsWith);
    }
}