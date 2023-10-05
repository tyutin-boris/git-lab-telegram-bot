package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.services.mr.api.CloseMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.CLOSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrCloseEventHandler implements MrEventHandler {

    private final CloseMrService closeMrService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor()
                .getId();

        log.debug("Merge event action " + getAction() + ". MR id: " + mrId);

        closeMrService.deleteMessage(mrId, authorId);
    }

    @Override
    public Action getAction() {
        return CLOSE;
    }
}
