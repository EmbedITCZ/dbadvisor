package com.github.embeditcz.dbadvisor.core.issue;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@RequiredArgsConstructor
public class Issue {

    private String type;
    private String query;
    private String description;
    private String dataSourceName;
    private long weight;
    private String weightUnit;
    private LocalDateTime timestamp;
    private String[] stackTrace;
    private Map<String, Object> metadata;

}
