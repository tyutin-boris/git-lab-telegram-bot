package ru.git.lab.bot.api.mr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DetailedMergeStatus {

    MERGEABLE("mergeable"),
    NOT_OPEN("not_open"),
    DRAFT_STATUS("draft_status"),
    CHECKING("checking"),
    CI_STILL_RUNNING("ci_still_running");

    private final String name;
}
