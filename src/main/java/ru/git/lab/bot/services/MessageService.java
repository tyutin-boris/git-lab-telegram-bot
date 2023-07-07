package ru.git.lab.bot.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;

public interface MessageService {

    void saveMessage(Message message, ObjectAttributes attributes);
}
