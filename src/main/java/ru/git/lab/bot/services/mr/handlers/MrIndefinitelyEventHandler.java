package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;
import ru.git.lab.bot.utils.ObjectAttributesUtils;
import org.springframework.stereotype.Service;

import static ru.git.lab.bot.api.mr.Action.INDEFINITELY;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrIndefinitelyEventHandler implements MrEventHandler {

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Action action = ObjectAttributesUtils.getAction(objectAttributes);

        log.debug("Mr handler for this action " + action + " not found. MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return INDEFINITELY;
    }
}
