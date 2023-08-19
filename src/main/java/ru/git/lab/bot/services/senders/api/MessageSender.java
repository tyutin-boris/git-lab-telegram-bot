package ru.git.lab.bot.services.senders.api;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface MessageSender {
    Optional<Message> sendMessage(String text, Long chatId);

    Optional<Message> updateMessage(String text, Long chatId, Integer messageId);

    boolean deleteMessage(Long chatId, Integer messageId);
}
