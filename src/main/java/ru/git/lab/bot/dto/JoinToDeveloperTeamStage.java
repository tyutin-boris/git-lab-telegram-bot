package ru.git.lab.bot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JoinToDeveloperTeamStage {
    REQUEST_USERNAME(0),

    RECEIVE_USERNAME(1);

    private final int step;
}
