package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PipelineStatus {
    PENDING("pending"),
    FAILED("failed"),
    RUNNING("running"),
    SUCCESS("success");

    private final String value;
    @JsonValue
    public String getValue() {
        return value;
    }
}
