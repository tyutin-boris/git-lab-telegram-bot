package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Changes {
    @JsonProperty("updated_by_id")
    UpdatedById updatedById;
    @JsonProperty("updated_at")
    UpdatedAt updatedAt;
    Labels labels;
    @JsonProperty("last_edited_at")
    LastEditedAt lastEditedAt;
    @JsonProperty("last_edited_by_id")
    LastEditedById lastEditedById;
}
