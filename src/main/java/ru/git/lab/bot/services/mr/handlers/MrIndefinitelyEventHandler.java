package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.INDEFINITELY;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrIndefinitelyEventHandler implements MrEventHandler {

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        Action action = mergeRequest.getAction();

        log.debug("Mr handler for this action " + action + " not found. MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return INDEFINITELY;
    }
}
