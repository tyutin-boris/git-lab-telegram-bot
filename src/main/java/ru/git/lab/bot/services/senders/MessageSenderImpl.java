package ru.git.lab.bot.services.senders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.git.lab.bot.services.bot.TelegramBot;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final TelegramBot telegramBot;

    @Override
    public Optional<Message> sendMessage(String text, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableHtml(true);

        return sendMessage(message);
    }

    @Override
    public Optional<Message> updateMessage(String text, Long chatId, Integer messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText(text);
        message.enableHtml(true);

        return updateMessage(message);
    }

    @Override
    public boolean deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage message = new DeleteMessage();
        message.setChatId(chatId);
        message.setMessageId(messageId);

        return deleteMessage(message);
    }

    private Optional<Message> sendMessage(SendMessage message) {
        String chatId = message.getChatId();

        try {
            Message execute = telegramBot.execute(message);

            log.debug("Send message to chat with id " + chatId);
            return Optional.ofNullable(execute);
        } catch (TelegramApiException e) {
            log.error("Failed to send message: chat id " + chatId, e);
            return Optional.empty();
        }
    }

    private Optional<Message> updateMessage(EditMessageText message) {
        String chatId = message.getChatId();

        try {
            Message updatedMessage = (Message) telegramBot.execute(message);

            log.debug("Update message with id " + message.getMessageId() + " from chat with id " + chatId);
            return Optional.ofNullable(updatedMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to update message: chat id " + chatId, e);
            return Optional.empty();
        }
    }

    private boolean deleteMessage(DeleteMessage message) {
        String chatId = message.getChatId();
        Integer messageId = message.getMessageId();

        try {
            boolean isDeleted = telegramBot.execute(message);

            log.debug("Delete message with id " + messageId + " from chat with id " + chatId);
            return isDeleted;
        } catch (TelegramApiException e) {
            log.error("Failed to delete message with id " + messageId + " from chat with id " + chatId, e);
        }
        return false;
    }
}
