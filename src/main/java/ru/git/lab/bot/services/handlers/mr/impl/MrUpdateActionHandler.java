package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.Action;
import ru.git.lab.bot.api.MergeRequest;
import ru.git.lab.bot.api.ObjectAttributes;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import org.springframework.stereotype.Service;

import static ru.git.lab.bot.api.Action.UPDATE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUpdateActionHandler implements MrActionHandler {

    @Override
    public void handleAction(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }
}
