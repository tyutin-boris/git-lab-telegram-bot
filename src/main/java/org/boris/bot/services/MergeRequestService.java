package org.boris.bot.services;

import org.boris.bot.api.MergeRequest;

public interface MergeRequestService {

    void sendMergeRequestOpen(MergeRequest request);

    void sendMergeRequestClose(MergeRequest request);
}
