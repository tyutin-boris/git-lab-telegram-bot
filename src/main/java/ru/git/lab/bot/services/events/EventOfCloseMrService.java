package ru.git.lab.bot.services.events;

public interface EventOfCloseMrService {
    void deleteMessage(Long mrId, Long authorId);
}
