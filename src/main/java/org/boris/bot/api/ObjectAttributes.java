package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import lombok.Data;
import org.boris.bot.api.serializer.OffsetDateTimeDeserializer;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ObjectAttributes {
    Long id;
    Long iid;
    @JsonProperty("target_branch")
    String targetBranch;
    @JsonProperty("source_branch")
    String sourceBranch;
    @JsonProperty("source_project_id")
    Long sourceProjectId;
    @JsonProperty("author_id")
    Long authorId;
    @JsonProperty("assignee_ids")
    List<Integer> assigneeIds;
    @JsonProperty("assignee_id")
    Long assigneeId;
    @JsonProperty("reviewer_ids")
    List<Integer> reviewerIds;
    String title;
    @JsonProperty("created_at")
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    OffsetDateTime createdAt;
    @JsonProperty("updated_at")
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    OffsetDateTime updatedAt;
    @JsonProperty("last_edited_at")
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    OffsetDateTime lastEditedAt;
    @JsonProperty("last_edited_by_id")
    Long lastEditedById;
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
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    Action action;
    @JsonProperty("detailed_merge_status")
    String detailedMergeStatus;
}
