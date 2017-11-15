package com.github.embeditcz.dbadvisor.core.internal.issue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class IssueObjectMapper {

    private final ObjectMapper objectMapper;

    public IssueObjectMapper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }

}
