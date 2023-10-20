package ru.git.lab.bot.services.mr.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.MessageChatsEntity;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
        Set<MessageChatsEntity> chats = message.getChats();

        for (MessageChatsEntity chat : chats) {
            if (Objects.isNull(chat.getTgId())) {
                sendMessage(message, text, chat);
            } else {
                updateMessage(message, text, chat);
            }
        }

        tgMrMessageService.save(message);
    }

    private void sendMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        messageSender.sendMessage(text, chat.getChatId())
                .ifPresent(sent -> setTgIdAndChangeDraftStatus(sent.getMessageId(), chat, message));
    }

    private void updateMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        Integer tgId = Optional.of(chat.getTgId())
                .orElseThrow(() -> new RuntimeException("Update messages is failure because tg is null. tgMrMessageId: " + message.getId()));

        messageSender.updateMessage(text, chat.getChatId(), tgId);
        tgMrMessageService.updateText(text, message);
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }

    private void setTgIdAndChangeDraftStatus(Integer tgId, MessageChatsEntity chat, TgMrMessageEntity message) {
        chat.setTgId(tgId);
        message.setDraft(false);
        log.debug("Update tgId: {}, for tg message with id: {}, and draft status false", tgId, message.getId());
    }
}
