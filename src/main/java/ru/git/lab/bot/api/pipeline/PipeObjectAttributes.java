package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PipeObjectAttributes {

    private Long id;

    private String iid;

    private String ref;

    private Boolean tag;

    private String sha;

    @JsonProperty("before_sha")
    private String beforeSha;

    private String source;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private PipelineStatus status;

    @JsonProperty("detailed_status")
    private String detailedStatus;

    private List<String> stages;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("finished_at")
    private String finishedAt;

    private String duration;

    @JsonProperty("queued_duration")
    private String queuedDuration;

    private Map<String, String> variables;
}
