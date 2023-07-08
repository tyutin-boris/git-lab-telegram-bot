package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.services.MessageService;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message, ObjectAttributes attributes, User user) {
        MessageEntity messageEntity = createMessage(message, attributes, user);
        messageRepository.save(messageEntity);
        log.debug("Save message with id " + messageEntity.getMessageId() + ", authorUsername " +
                          messageEntity.getAuthorUsername());
    }

    @Override
    public MessageToDelete getMessageToDelete(Long mrId, String email, String username) {
        return messageRepository.getMessageToDelete(mrId, email, username);
    }

    private MessageEntity createMessage(Message message, ObjectAttributes attributes, User user) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setMessageId(message.getMessageId());
        messageEntity.setChatId(message.getChatId());
        messageEntity.setMrId(attributes.getId());
        messageEntity.setAuthorId(attributes.getAuthorId());
        messageEntity.setAuthorEmail(user.getEmail());
        messageEntity.setAuthorUsername(user.getUsername());
        messageEntity.setCreateDateTime(OffsetDateTime.now());

        return messageEntity;
    }
}
