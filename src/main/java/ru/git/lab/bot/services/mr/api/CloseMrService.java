package ru.git.lab.bot.services.mr.api;

public interface CloseMrService {
    void deleteMessage(long mrId, long authorId);
}
