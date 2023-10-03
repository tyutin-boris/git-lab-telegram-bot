package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.services.api.ApproveService;
import ru.git.lab.bot.services.mr.api.ReactionMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import java.util.List;

import static ru.git.lab.bot.api.mr.Action.UNAPPROVED;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;
import static ru.git.lab.bot.utils.UserUtils.getUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUnapprovedEventHandler implements MrEventHandler {

    private final ApproveService approveService;
    private final ReactionMrService reactionMrService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long userId = mergeRequest.getUser().getId();

        List<ApproveEntity> approvalsForMrByAuthorId = approveService.findAllByMrIdAndAuthorId(mrId, userId);
        approveService.deleteAll(approvalsForMrByAuthorId);

        reactionMrService.addReactionToMessage(mergeRequest);

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return UNAPPROVED;
    }
}
