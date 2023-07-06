package org.boris.bot.services.handlers.mr;

import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;

public interface MrActionHandler {

    void handleAction(MergeRequest request);

    default Action getAction() {
        return Action.INDEFINITELY;
    }
}
