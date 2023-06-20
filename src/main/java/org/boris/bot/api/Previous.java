package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Previous {
    Long id;
    String title;
    String color;
    @JsonProperty("project_id")
    Long projectId;
    @JsonProperty("created_at")
    OffsetDateTime createdAt;
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt;
    Boolean template;
    String description;
    String type;
    @JsonProperty("group_id")
    Long groupId;
}
