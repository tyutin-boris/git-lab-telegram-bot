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

import java.util.Objects;

import static ru.git.lab.bot.api.mr.Action.UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrUpdateEventHandler implements MrEventHandler {

    private final TgMrMessageService tgMrMessageService;
    private final MessageSender messageSender;
    private final MrTextMessageService mrTextMessageService;

    @Override
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor().getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        TgMrMessageEntity message = tgMrMessageService.getMessageByMrIdAndAuthorId(mrId, authorId);
        String text = mrTextMessageService.createMergeRequestTextMessage(mergeRequest);

        if(Objects.isNull(message.getTgId())) {
            messageSender.sendMessage(text, message.getChatId())
                    .ifPresent(sent -> tgMrMessageService.updateTgIdAndDraftStatus(sent.getMessageId(), message));
        } else {
            messageSender.updateMessage(text, message.getChatId(), message.getTgId());
            tgMrMessageService.updateText(text, message);
        }
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }
}
