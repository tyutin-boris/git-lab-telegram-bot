package ru.git.lab.bot.services.api;

import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface MergeRequestService {

    void handleEvent(MergeRequestEvent event);
}
