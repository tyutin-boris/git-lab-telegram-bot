package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;
import ru.git.lab.bot.services.senders.api.MessageSender;

import static ru.git.lab.bot.api.mr.Action.UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUpdateEventHandler implements MrEventHandler {

    private final TgMrMessageService messageService;
    private final MessageSender messageSender;
    private final MrTextMessageService mrTextMessageService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor().getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        String text = mrTextMessageService.createMergeRequestTextMessage(mergeRequest);
        TgMrMessageEntity message = messageService.getMessageByMrIdAndAuthorId(mrId, authorId);

        messageSender.updateMessage(text, message.getChatId(), message.getTgId());
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }
}
