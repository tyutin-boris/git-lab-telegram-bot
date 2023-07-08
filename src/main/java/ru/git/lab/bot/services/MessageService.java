package ru.git.lab.bot.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.dto.MessageToDelete;

import java.util.List;

public interface MessageService {

    void saveMessage(Message message, ObjectAttributes attributes, User user);

    List<MessageToDelete> getMessageToDelete(Long mrId, String email, String username);

    void deleteMessages(List<MessageToDelete> messages);
}
