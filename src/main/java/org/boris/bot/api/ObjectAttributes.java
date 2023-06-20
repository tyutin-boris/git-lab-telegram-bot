package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ObjectAttributes {
    int id;
    int iid;
    @JsonProperty("target_branch")
    String targetBranch;
    @JsonProperty("source_branch")
    String sourceBranch;
    @JsonProperty("source_project_id")
    int sourceProjectId;
    @JsonProperty("author_id")
    int authorId;
    @JsonProperty("assignee_ids")
    List<Integer> assigneeIds;
    @JsonProperty("assignee_id")
    int assigneeId;
    @JsonProperty("reviewer_ids")
    List<Integer> reviewerIds;
    String title;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    @JsonProperty("last_edited_at")
    LocalDateTime lastEditedAt;
    @JsonProperty("last_edited_by_id")
    int lastEditedById;
    @JsonProperty("milestone_id")
    Long milestoneId;
    @JsonProperty("state_id")
    Long stateId;
    String state;
    @JsonProperty("blocking_discussions_resolved")
    Boolean blockingDiscussionsResolved;
    @JsonProperty("work_in_progress")
    Boolean workInProgress;
    @JsonProperty("first_contribution")
    Boolean firstContribution;
    @JsonProperty("merge_status")
    String mergeStatus;
    @JsonProperty("target_project_id")
    Long targetProjectId;
    String description;
    @JsonProperty("total_time_spent")
    Long totalTimeSpent;
    @JsonProperty("time_change")
    Long timeChange;
    @JsonProperty("human_total_time_spent")
    String humanTotalTimeSpent;
    @JsonProperty("human_time_change")
    String humanTimeChange;
    @JsonProperty("human_time_estimate")
    String humanTimeEstimate;
    String url;
    Source source;
    Target target;
    @JsonProperty("last_commit")
    LastCommit lastCommit;
    List<Label> labels;
    String action;
    @JsonProperty("detailed_merge_status")
    String detailedMergeStatus;
}
