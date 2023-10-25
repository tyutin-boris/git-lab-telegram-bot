package ru.git.lab.bot.services.mr.handlers;

import jakarta.transaction.Transactional;
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
    @Transactional
    public void handleEvent(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor().getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        TgMrMessageEntity message = tgMrMessageService.getMessageByMrIdAndAuthorId(mrId, authorId);

        log.debug("try update tg message. id: {}", message.getId());
        String text = mrTextMessageService.createMrMessage(mrId);
        Set<MessageChatsEntity> chats = message.getChats();

        for (MessageChatsEntity chat : chats) {
            if (Objects.isNull(chat.getTgId())) {
                sendMessage(message, text, chat);
                log.debug("Sent tg message id: {}, chatId: {}", message.getId(), chat.getChatId());
            } else if (mergeRequest.isDraft()) {
                deleteMessage(message, text, chat);
                log.debug("Tg message was deleted. id: {}, chatId: {}", message.getId(), chat.getChatId());
            } else {
                updateMessage(message, text, chat);
                log.debug("Updated tg message. id: {}, chatId: {}", message.getId(), chat.getChatId());
            }
        }

        updateTgMrMessage(mergeRequest, message);
    }

    private void updateTgMrMessage(MergeRequestDto mergeRequest, TgMrMessageEntity message) {
        String newText = mrTextMessageService.createMergeRequestText(mergeRequest);
        message.setText(newText);
        tgMrMessageService.save(message);
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }

    private void sendMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        messageSender.sendMessage(text, chat.getChatId())
                .ifPresent(sent -> saveMessage(sent.getMessageId(), chat, message));
    }

    private void deleteMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        boolean isDeleted = messageSender.deleteMessage(chat.getChatId(), chat.getTgId());

        message.setText(text);
        message.setDelete(isDeleted);
        message.setDraft(true);
        chat.setTgId(null);
    }

    private void updateMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        Integer tgId = Optional.of(chat.getTgId())
                .orElseThrow(() -> new RuntimeException("Update messages is failure because tg is null. tgMrMessageId: " + message.getId()));

        message.setText(text);
        messageSender.updateMessage(text, chat.getChatId(), tgId);
    }

    private void saveMessage(Integer tgId, MessageChatsEntity chat, TgMrMessageEntity message) {
        chat.setTgId(tgId);
        message.setDraft(false);
        message.setDelete(false);
        log.debug("Update tgId: {}, for tg message with id: {}, and draft status false", tgId, message.getId());
    }
}
