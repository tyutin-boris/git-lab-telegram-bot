package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Current {
    Long id;
    String title;
    String color;
    @JsonProperty("project_id")
    Long projectId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_at")
    OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt;
    Boolean template;
    String description;
    String type;
    @JsonProperty("group_id")
    Long groupId;
}
