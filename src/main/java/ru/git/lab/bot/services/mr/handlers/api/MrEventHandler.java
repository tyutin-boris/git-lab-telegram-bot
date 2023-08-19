package ru.git.lab.bot.services.mr.handlers.api;

import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface MrEventHandler {

    void handleEvent(MergeRequestEvent event);

    default Action getAction() {
        return Action.INDEFINITELY;
    }
}
