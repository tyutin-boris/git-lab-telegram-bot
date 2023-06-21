package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class Previous {
    Long id;
    String title;
    String color;
    @JsonProperty("project_id")
    Long projectId;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    Boolean template;
    String description;
    String type;
    @JsonProperty("group_id")
    Long groupId;
}
