package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.git.lab.bot.api.mr.User;

import java.util.List;

@Data
public class Builds {

    private String id;

    private String stage;

    private String name;

    private String status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("started_at")
    private String startedAt;

    @JsonProperty("finished_at")
    private String finishedAt;

    private String duration;

    @JsonProperty("queued_duration")
    private String queuedDuration;

    @JsonProperty("failure_reason")
    private String failureReason;

    private String when;

    private String manual;

    @JsonProperty("allow_failure")
    private Boolean allowFailure;

    private List<User> users;

    private String runner;

    @JsonProperty("artifacts_file")
    private ArtifactsFile artifactsFile;

    private String environment;
}
