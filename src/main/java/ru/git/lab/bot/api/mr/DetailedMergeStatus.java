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
    CI_STILL_RUNNING("ci_still_running"),
    INDEFINITELY("indefinitely");

    private final String name;

    public static DetailedMergeStatus getStatus(String name) {
        for (DetailedMergeStatus status : DetailedMergeStatus.values()) {
            if (status.getName()
                    .equals(name)) {
                return status;
            }
        }
        return INDEFINITELY;
    }
}
