package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.services.api.ApproveService;
import ru.git.lab.bot.services.mr.api.ReactionMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.APPROVED;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;
import static ru.git.lab.bot.utils.UserUtils.getUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrApprovedEventHandler implements MrEventHandler {

    private final ApproveService approveService;
    private final ReactionMrService reactionMrService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        User user = getUser(event);

        approveService.save(mrId, user);
        reactionMrService.addReactionToMessage(event);

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return APPROVED;
    }
}
