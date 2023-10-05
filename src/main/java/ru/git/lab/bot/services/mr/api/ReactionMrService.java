package ru.git.lab.bot.services.mr.api;

import ru.git.lab.bot.dto.MergeRequestDto;

public interface ReactionMrService {
    void addReactionToMessage(MergeRequestDto mergeRequest);
}
