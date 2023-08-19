package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.APPROVAL;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrApprovalEventHandler implements MrEventHandler {

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return APPROVAL;
    }
}
