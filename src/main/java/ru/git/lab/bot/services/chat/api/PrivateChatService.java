package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface PrivateChatService {

    void handle(Message message);
}
