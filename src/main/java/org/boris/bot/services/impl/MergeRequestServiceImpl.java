package org.boris.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.model.repository.ChatRepository;
import org.boris.bot.services.MergeRequestService;
import org.boris.bot.services.handlers.mr.MrActionHandler;
import org.boris.bot.services.senders.MergeRequestSender;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.boris.bot.utils.ObjectAttributesUtils.getAction;
import static org.boris.bot.utils.ObjectAttributesUtils.getObjectAttributes;

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
