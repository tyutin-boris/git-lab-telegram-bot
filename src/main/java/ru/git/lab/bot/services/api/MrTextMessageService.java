package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.MergeRequestDto;

public interface MrTextMessageService {
    String createMergeRequestText(MergeRequestDto mergeRequest);
    String createMrMessage(Long mrId);
}
