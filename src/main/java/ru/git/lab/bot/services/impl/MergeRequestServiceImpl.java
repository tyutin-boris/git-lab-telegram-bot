package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.MergeRequestService;
import ru.git.lab.bot.services.UserService;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;

import java.util.Map;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getAction;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private final Map<Action, MrActionHandler> actionHandlers;
    private final UserService userService;

    @Override
    public void sendMergeRequestMessage(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Action action = getAction(objectAttributes);

        actionHandlers.get(action)
                .handleAction(request);

        userService.saveUser(request.getUser());
    }
}
