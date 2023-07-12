package ru.git.lab.bot.services.senders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.git.lab.bot.services.bot.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final TelegramBot telegramBot;

    @Override
    public Message sendMessage(String text, Long chatId) {
        try {
            return telegramBot.sendMessage(text, chatId);
        } catch (TelegramApiException e) {
            log.warn("Failed to send message: chat id " + chatId, e);
        }
        return null;
    }

    @Override
    public boolean deleteMessage(Long chatId, Integer messageId) {
        try {
            return telegramBot.deleteMessage(chatId, messageId);
        } catch (TelegramApiException e) {
            log.warn("Failed to delete message: chat id " + chatId + ", message id " + messageId, e);
        }
        return false;
    }

    @Override
    public Message sendSticker(Long chatId, Integer messageId) {
//        try {
//            return telegramBot.sendSticker(chatId, messageId);
//        } catch (TelegramApiException e) {
//            log.warn("Failed to send sticker: chat id " + chatId + ", message id " + messageId, e);
//        }
        return null;
    }

    @Override
    public Message updateMessage(String text, Long chatId, Integer messageId) {
        try {
            return telegramBot.updateMessage(text, chatId, messageId);
        } catch (TelegramApiException e) {
            log.warn("Failed to update message: chat id " + chatId, e);
        }
        return null;
    }
}
