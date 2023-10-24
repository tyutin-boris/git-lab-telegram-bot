package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MergeRequest {

    private Long id;

    private Long iid;

    private String title;

    @JsonProperty("source_branch")
    private String sourceBranch;

    @JsonProperty("source_project_id")
    private Long sourceProjectId;

    @JsonProperty("target_branch")
    private String targetBranch;

    @JsonProperty("target_project_id")
    private Long targetProjectId;

    private String state;

    @JsonProperty("merge_status")
    private String mergeStatus;

    @JsonProperty("detailed_merge_status")
    private String detailedMergeStatus;

    private String url;
}
