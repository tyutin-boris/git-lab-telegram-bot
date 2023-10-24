package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Runner {

    private Long id;

    private String description;

    @JsonProperty("runner_type")
    private String runnerType;

    private Boolean active;

    @JsonProperty("is_shared")
    private Boolean isShared;

    private List<String> tags;
}
