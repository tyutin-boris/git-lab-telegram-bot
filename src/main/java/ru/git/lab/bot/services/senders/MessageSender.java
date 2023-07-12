package ru.git.lab.bot.services.senders;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageSender {
    Message sendMessage(String text, Long chatId);
    boolean deleteMessage(Long chatId, Integer messageId);
    Message sendSticker(Long chatId, Integer messageId);

    Message updateMessage(String text, Long chatId, Integer messageId);
}
