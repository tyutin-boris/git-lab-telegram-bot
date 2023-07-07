package org.boris.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.model.repository.ChatRepository;
import org.boris.bot.services.handlers.mr.MrActionHandler;
import org.boris.bot.services.senders.MergeRequestSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.boris.bot.api.Action.REOPEN;
import static org.boris.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static org.boris.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrReopenActionHandler implements MrActionHandler {

    private final MergeRequestSender sender;
    private final ChatRepository chatRepository;

    @Override
    public void handleAction(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();
        log.debug("Merge request action REOPEN. MR id: " + mrId);

        String text = createMergeRequestMessage(request);
        List<Long> chatsId = chatRepository.getAllId();

        for (Long id : chatsId) {
            sender.sendMessage(text, id);
        }
    }

    @Override
    public Action getAction() {
        return REOPEN;
    }
}
