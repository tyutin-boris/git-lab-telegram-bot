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
        long authorId = mergeRequest.getAuthor()
                .getId();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        TgMrMessageEntity tgMrMessage = tgMrMessageService.getMessageByMrIdAndAuthorId(mrId, authorId);

        log.debug("try update tg message. id: {}", tgMrMessage.getId());
        String newText = mrTextMessageService.createMergeRequestText(mergeRequest);
        String message = mrTextMessageService.createMrMessage(mrId, newText);
        Set<MessageChatsEntity> chats = tgMrMessage.getChats();

        for (MessageChatsEntity chat : chats) {
            Integer tgId = chat.getTgId();
            if (Objects.isNull(tgId)) {
                messageSender.sendMessage(message, chat.getChatId())
                        .ifPresent(sent -> saveMessage(sent.getMessageId(), newText, chat, tgMrMessage));
                log.debug("Sent tg message id: {}, chatId: {}", tgMrMessage.getId(), chat.getChatId());
            } else if (mergeRequest.isDraft()) {
                boolean isDeleted = messageSender.deleteMessage(chat.getChatId(), chat.getTgId());
                if (isDeleted) {
                    deleteMessage(tgMrMessage, newText, chat);
                    log.debug("Tg message was deleted. id: {}, chatId: {}", tgMrMessage.getId(), chat.getChatId());
                }
            } else {
                messageSender.updateMessage(message, chat.getChatId(), tgId);
                tgMrMessage.setText(newText);
                log.debug("Updated tg message. id: {}, chatId: {}", tgMrMessage.getId(), chat.getChatId());
            }
        }

        tgMrMessageService.save(tgMrMessage);
    }

    @Override
    public Action getAction() {
        return UPDATE;
    }

    private void deleteMessage(TgMrMessageEntity message, String text, MessageChatsEntity chat) {
        message.setText(text);
        message.setDelete(true);
        message.setDraft(true);
        chat.setTgId(null);
    }

    private void saveMessage(Integer tgId, String text, MessageChatsEntity chat, TgMrMessageEntity message) {
        chat.setTgId(tgId);
        message.setText(text);
        message.setDraft(false);
        message.setDelete(false);
        log.debug("Update tgId: {}, for tg message with id: {}, and draft status false", tgId, message.getId());
    }
}
