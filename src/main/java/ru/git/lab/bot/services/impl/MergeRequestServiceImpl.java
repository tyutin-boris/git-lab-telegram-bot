package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.Action;
import ru.git.lab.bot.api.MergeRequest;
import ru.git.lab.bot.api.ObjectAttributes;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.MergeRequestService;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import ru.git.lab.bot.services.senders.MergeRequestSender;
import org.springframework.stereotype.Service;

import java.util.Map;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getAction;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private final MergeRequestSender sender;
    private final ChatRepository chatRepository;
    private final Map<Action, MrActionHandler> actionHandlers;
//    private final MessageRepository messageRepository;

    @Override
    public void sendMergeRequestMessage(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Action action = getAction(objectAttributes);

        actionHandlers.get(action)
                .handleAction(request);
    }
}
