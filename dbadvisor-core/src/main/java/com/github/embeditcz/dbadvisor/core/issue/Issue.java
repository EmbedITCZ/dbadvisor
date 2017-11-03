package com.github.embeditcz.dbadvisor.core.issue;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Issue {

    private String type;
    private String query;
    private String description;
    private long weight;
    private LocalDateTime timestamp;
    private String[] stackTrace;
    private Map<String, Object> metadata;

}
