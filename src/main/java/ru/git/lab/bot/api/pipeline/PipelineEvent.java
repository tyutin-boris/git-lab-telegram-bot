package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.User;

@Data
public class PipelineEvent {

    @JsonProperty("object_kind")
    private String objectKind;

    @JsonProperty("object_attributes")
    private PipeObjectAttributes objectAttributes;

    @JsonProperty("merge_request")
    private MergeRequest mergeRequest;

    private User user;

    private PipeProject project;

    private Commit commit;

    private Builds builds;
}
