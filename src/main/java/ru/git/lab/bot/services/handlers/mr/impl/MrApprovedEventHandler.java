package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;
import ru.git.lab.bot.services.senders.MergeRequestSender;

import java.util.List;

import static ru.git.lab.bot.api.mr.Action.APPROVED;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrApprovedEventHandler implements MrEventHandler {

    private final MergeRequestSender sender;
    private final MessageRepository messageRepository;

    @Override
    public void handleEvent(MergeRequestEvent request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();

        List<MessageEntity> messageEntity = messageRepository.findByMrIdAndAuthorId(mrId, authorId);
        messageEntity.forEach(e -> sender.sendSticker(e.getChatId(), e.getMessageId()));

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return APPROVED;
    }
}
