package org.boris.bot.services;

import org.boris.bot.api.MergeRequest;

public interface MergeRequestService {

    void sendMergeRequestMessage(MergeRequest request);
}
