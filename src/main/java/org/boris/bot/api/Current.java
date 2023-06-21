package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


@Data
public class Current {
    Long id;
    String title;
    String color;
    @JsonProperty("project_id")
    Long projectId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Zagreb")
    @JsonProperty("created_at")
    Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Zagreb")
    @JsonProperty("updated_at")
    Date updatedAt;
    Boolean template;
    String description;
    String type;
    @JsonProperty("group_id")
    Long groupId;
}
