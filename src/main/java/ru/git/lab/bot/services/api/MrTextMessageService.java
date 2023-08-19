package ru.git.lab.bot.services.api;

import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.util.List;

public interface MrTextMessageService {
    String createMergeRequestTextMessage(MergeRequestEvent event);

    String createMergeRequestTextMessageWithApprove(MergeRequestEvent event, List<ApproveEntity> approves);
}
