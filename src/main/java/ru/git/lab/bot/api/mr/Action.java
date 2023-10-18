package ru.git.lab.bot.api.mr;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Action {

    OPEN("open"),

    CLOSE("close"),

    REOPEN("reopen"),

    UPDATE("update"),

    APPROVED("approved"),

    UNAPPROVED("unapproved"),

    APPROVAL("approval"),

    UNAPPROVAL("unapproval"),

    MERGE("merge"),

    INDEFINITELY("indefinitely");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
