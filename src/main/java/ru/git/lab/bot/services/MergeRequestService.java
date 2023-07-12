package ru.git.lab.bot.services;

import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface MergeRequestService {

    void handleEvent(MergeRequestEvent request);
}
