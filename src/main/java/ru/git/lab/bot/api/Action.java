package ru.git.lab.bot.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Action {
    @JsonProperty("open")
    OPEN,
    @JsonProperty("close")
    CLOSE,
    @JsonProperty("reopen")
    REOPEN,
    @JsonProperty("update")
    UPDATE,
    @JsonProperty("approved")
    APPROVED,
    @JsonProperty("unapproved")
    UNAPPROVED,
    @JsonProperty("approval")
    APPROVAL,
    @JsonProperty("unapproval")
    UNAPPROVAL,
    @JsonProperty("merge")
    MERGE,
    INDEFINITELY
}
