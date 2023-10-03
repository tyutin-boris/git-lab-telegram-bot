package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.services.mr.api.CreateMrService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrOpenEventHandler implements MrEventHandler {

    private final CreateMrService createMrService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();

        log.debug("Merge event action " + getAction() + ". MR id: " + mrId);

        createMrService.sendAndSaveMessage(mergeRequest);
    }

    @Override
    public Action getAction() {
        return Action.OPEN;
    }
}
