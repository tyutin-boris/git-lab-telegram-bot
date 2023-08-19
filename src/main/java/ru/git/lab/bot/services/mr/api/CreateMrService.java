package ru.git.lab.bot.services.mr.api;

import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface CreateMrService {
    void sendAndSaveMessage(MergeRequestEvent request);
}
