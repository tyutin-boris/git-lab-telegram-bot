package ru.git.lab.bot.dto;

import lombok.Data;

@Data
public class MessageToDelete {
    Long chatId;
    Integer messageId;
}
