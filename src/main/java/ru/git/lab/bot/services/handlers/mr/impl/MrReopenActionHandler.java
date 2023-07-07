package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.api.Action;
import ru.git.lab.bot.api.MergeRequest;
import ru.git.lab.bot.api.ObjectAttributes;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import ru.git.lab.bot.services.senders.MergeRequestSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static ru.git.lab.bot.api.Action.REOPEN;
import static ru.git.lab.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

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
        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

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
