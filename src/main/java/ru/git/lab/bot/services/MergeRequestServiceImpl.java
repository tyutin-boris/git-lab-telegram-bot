package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.api.MergeRequestService;
import ru.git.lab.bot.services.api.UserService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import java.util.Map;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getAction;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private final UserService userService;
    private final Map<Action, MrEventHandler> eventHandlers;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Action action = getAction(objectAttributes);

        userService.saveUserIfNotExist(event.getUser());
        eventHandlers.get(action).handleEvent(event);
    }
}
