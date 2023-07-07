package ru.git.lab.bot.services;

import ru.git.lab.bot.api.mr.MergeRequest;

public interface MergeRequestService {

    void sendMergeRequestMessage(MergeRequest request);
}
