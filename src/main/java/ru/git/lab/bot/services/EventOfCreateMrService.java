package ru.git.lab.bot.services;

import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface EventOfCreateMrService {
    void sendAndSaveMessage(MergeRequestEvent request);
}
