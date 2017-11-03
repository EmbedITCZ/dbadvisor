package com.github.embeditcz.dbadvisor.core.issue;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

@Value
public class Issue {

    private String type;
    private String query;
    private String description;
    private long weight;
    private String weightUnit;
    private LocalDateTime timestamp;
    private String[] stackTrace;
    private Map<String, Object> metadata;
    @JsonIgnore
    private String sortingKey;

    public Issue(String type, String query, String description, long weight, String weightUnit, LocalDateTime timestamp, String[] stackTrace,
            Map<String, Object> metadata) {
        this.type = type;
        this.query = query;
        this.description = description;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.timestamp = timestamp;
        this.stackTrace = stackTrace;
        this.metadata = metadata;
        this.sortingKey = type + "_" + String.format("%06d", weight) + "_" + query;
    }

}
