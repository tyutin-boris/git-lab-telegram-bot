package ru.git.lab.bot.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;

import java.util.List;

public interface MessageService {

    void saveMessage(Message message, ObjectAttributes attributes);

    List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId);

    void deleteMessages(List<MessageToDelete> messages);

    MessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId);
}
