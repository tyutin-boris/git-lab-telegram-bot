package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;
import org.springframework.stereotype.Service;

import static ru.git.lab.bot.api.mr.Action.UPDATE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUpdateEventHandler implements MrEventHandler {

    @Override
    public void handleEvent(MergeRequestEvent request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }
}
