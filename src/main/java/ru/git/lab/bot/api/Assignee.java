package ru.git.lab.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Assignee {
    Long id;
    String name;
    String username;
    @JsonProperty("avatar_url")
    String avatarUrl;
}
