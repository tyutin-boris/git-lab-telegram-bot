package ru.git.lab.bot.services.mr.handlers.api;

import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;

public interface MrEventHandler {

    void handleEvent(MergeRequestDto mergeRequest);

    default Action getAction() {
        return Action.INDEFINITELY;
    }
}
