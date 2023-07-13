package ru.git.lab.bot.services.events;

import ru.git.lab.bot.api.mr.MergeRequestEvent;

public interface EventOfReactionMrService {
    void addReactionToMessage(MergeRequestEvent event);
}
