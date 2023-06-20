package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MergeRequest {
    @JsonProperty("object_kind")
    String objectKind;
    @JsonProperty("event_type")
    String eventType;
    User user;
    Project project;
    Repository repository;
    @JsonProperty("object_attributes")
    ObjectAttributes objectAttributes;
    List<Label> labels;
    Changes changes;
    List<Assignee> assignees;
    List<Reviewer> reviewers;
}
