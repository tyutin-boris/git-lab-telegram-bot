package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Label {
    int id;
    String title;
    String color;
    @JsonProperty("project_id")
    int projectId;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    int template;
    String description;
    String type;
    @JsonProperty("group_id")
    int groupId;
}
