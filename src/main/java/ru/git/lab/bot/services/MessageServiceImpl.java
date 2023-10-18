package ru.git.lab.bot.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageSender messageSender;

    @Override
    public void saveMessage(Message message, MergeRequestDto mergeRequest) {
        MessageEntity messageEntity = createMessage(message, mergeRequest);
        messageRepository.save(messageEntity);
        log.debug("Save message with id " + messageEntity.getId() + ", authorId " + messageEntity.getAuthorId());
    }

    @Override
    public List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId) {
        return messageRepository.getMessageToDelete(mrId, authorId);
    }

    @Override
    @Transactional
    public void deleteMessages(List<MessageToDelete> messages) {
        for (MessageToDelete message : messages) {
            Integer id = message.getId();
            Long chatId = message.getChatId();

            boolean messageWasDelete = messageSender.deleteMessage(chatId, id);

            if (messageWasDelete) {
                messageRepository.deleteById(id);
                log.debug("Message was delete. id " + id + ", chatId " + chatId);
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

    private MessageEntity createMessage(Message message, MergeRequestDto mergeRequest) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setId(message.getMessageId());
        messageEntity.setChatId(message.getChatId());
        messageEntity.setMrId(mergeRequest.getMrId());
        messageEntity.setAuthorId(mergeRequest.getAuthor().getId());
        messageEntity.setCreateDateTime(OffsetDateTime.now());

        return messageEntity;
    }
}
