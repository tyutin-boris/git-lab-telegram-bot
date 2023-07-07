package org.boris.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.services.handlers.mr.MrActionHandler;
import org.springframework.stereotype.Service;

import static org.boris.bot.api.Action.CLOSE;
import static org.boris.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrCloseActionHandler implements MrActionHandler {

    @Override
    public void handleAction(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();

        log.warn("Merge request action CLOSE, message about MR with id " + mrId + " should delete");
    }

    @Override
    public Action getAction() {
        return CLOSE;
    }
}
