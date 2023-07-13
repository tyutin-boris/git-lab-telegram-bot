package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.services.ApproveService;
import ru.git.lab.bot.services.events.EventOfReactionMrService;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;

import java.util.List;

import static ru.git.lab.bot.api.mr.Action.UNAPPROVED;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;
import static ru.git.lab.bot.utils.UserUtils.getUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUnapprovedEventHandler implements MrEventHandler {

    private final ApproveService approveService;
    private final EventOfReactionMrService eventOfReactionMrService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long userId = getUser(event).getId();

        List<ApproveEntity> approvalsForMrByAuthorId = approveService.findAllByMrIdAndAuthorId(mrId, userId);
        approveService.deleteAll(approvalsForMrByAuthorId);

        eventOfReactionMrService.addReactionToMessage(event);

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return UNAPPROVED;
    }
}
