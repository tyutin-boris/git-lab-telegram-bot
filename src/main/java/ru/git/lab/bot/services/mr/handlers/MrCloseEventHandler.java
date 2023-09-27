package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.mr.api.CloseMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.CLOSE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrCloseEventHandler implements MrEventHandler {

    private final CloseMrService closeMrService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        long mrId = objectAttributes.getId();
        long authorId = objectAttributes.getAuthorId();

        log.debug("Merge event action " + getAction() + ". MR id: " + mrId);

        closeMrService.deleteMessage(mrId, authorId);
    }

    @Override
    public Action getAction() {
        return CLOSE;
    }
}
