package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Reviewer {
    int id;
    String name;
    String username;
    @JsonProperty("avatar_url")
    String avatarUrl;
}

