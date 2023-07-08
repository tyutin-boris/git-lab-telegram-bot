package ru.git.lab.bot.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageToDelete {
    UUID messageId;
    Long chatId;
    Integer telegramMessageId;

    public MessageToDelete(UUID messageId, Long chatId, Integer telegramMessageId) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.telegramMessageId = telegramMessageId;
    }
}
