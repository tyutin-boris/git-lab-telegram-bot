package ru.git.lab.bot.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageSender messageSender;

    @Override
    public void saveMessage(Message message, ObjectAttributes attributes) {
        MessageEntity messageEntity = createMessage(message, attributes);
        messageRepository.save(messageEntity);
        log.debug("Save message with id " + messageEntity.getMessageId() + ", authorId " + messageEntity.getAuthorId());
    }

    @Override
    public List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId) {
        return messageRepository.getMessageToDelete(mrId, authorId);
    }

    @Override
    @Transactional
    public void deleteMessages(List<MessageToDelete> messages) {
        for (MessageToDelete message : messages) {
            UUID messageId = message.getMessageId();
            Long chatId = message.getChatId();
            Integer telegramMessageId = message.getTelegramMessageId();
            boolean messageWasDelete = messageSender.deleteMessage(chatId, telegramMessageId);

            if (messageWasDelete) {
                messageRepository.deleteById(messageId);
                log.debug("Message was delete. id " + messageId + ", chatId " + chatId);
            }
        }
    }

    @Override
    public MessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId) {
        return messageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElseThrow(() -> new RuntimeException(
                        "Message not found. mrId: " + mrId + ", " + "authorId: " + authorId));
    }

    @Override
    public Optional<MessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId) {
        return messageRepository.findByMrIdAndAuthorId(mrId, authorId);
    }

    private MessageEntity createMessage(Message message, ObjectAttributes attributes) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setMessageId(message.getMessageId());
        messageEntity.setChatId(message.getChatId());
        messageEntity.setMrId(attributes.getId());
        messageEntity.setAuthorId(attributes.getAuthorId());
        messageEntity.setCreateDateTime(OffsetDateTime.now());

        return messageEntity;
    }
}
