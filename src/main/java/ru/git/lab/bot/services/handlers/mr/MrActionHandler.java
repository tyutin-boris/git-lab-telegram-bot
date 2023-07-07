package ru.git.lab.bot.services.handlers.mr;

import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequest;

public interface MrActionHandler {

    void handleAction(MergeRequest request);

    default Action getAction() {
        return Action.INDEFINITELY;
    }
}
