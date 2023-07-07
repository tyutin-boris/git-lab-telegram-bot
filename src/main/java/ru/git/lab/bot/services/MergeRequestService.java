package ru.git.lab.bot.services;

import ru.git.lab.bot.api.MergeRequest;

public interface MergeRequestService {

    void sendMergeRequestMessage(MergeRequest request);
}
