package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;
import ru.git.lab.bot.services.senders.MessageSender;

import static ru.git.lab.bot.api.mr.Action.UPDATE;
import static ru.git.lab.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUpdateEventHandler implements MrEventHandler {

    private final MessageService messageService;
    private final MessageSender messageSender;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        String text = createMergeRequestMessage(event);
        MessageEntity message = messageService.getMessageByMrIdAndAuthorId(mrId, authorId);

        messageSender.updateMessage(text, message.getChatId(), message.getMessageId());
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }
}
