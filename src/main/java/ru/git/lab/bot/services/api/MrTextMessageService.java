package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.util.List;

public interface MrTextMessageService {
    String createMergeRequestTextMessage(MergeRequestDto mergeRequest);

    String createMergeRequestTextMessageWithApprove(MergeRequestDto mergeRequest, List<ApproveEntity> approves);
}
