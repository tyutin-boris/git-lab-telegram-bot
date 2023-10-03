package ru.git.lab.bot.services.mr.api;

import ru.git.lab.bot.dto.MergeRequestDto;

public interface CreateMrService {

    void sendAndSaveMessage(MergeRequestDto mergeRequest);
}
