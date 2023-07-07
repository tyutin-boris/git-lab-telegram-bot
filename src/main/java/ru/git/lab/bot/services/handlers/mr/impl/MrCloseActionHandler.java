package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import org.springframework.stereotype.Service;

import static ru.git.lab.bot.api.mr.Action.CLOSE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrCloseActionHandler implements MrActionHandler {

    @Override
    public void handleAction(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();

        log.warn("Merge request action " + getAction() + ", message about MR with id " + mrId + " should delete");
    }

    @Override
    public Action getAction() {
        return CLOSE;
    }
}
