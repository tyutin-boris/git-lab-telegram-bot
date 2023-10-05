package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.services.api.ApproveService;
import ru.git.lab.bot.services.mr.api.ReactionMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.APPROVED;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrApprovedEventHandler implements MrEventHandler {

    private final ApproveService approveService;

    private final ReactionMrService reactionMrService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        UserDto user = mergeRequest.getUser();

        approveService.save(mrId, user);
        reactionMrService.addReactionToMessage(mergeRequest);

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return APPROVED;
    }
}
