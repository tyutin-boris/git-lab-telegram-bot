package ru.git.lab.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageToDelete {

    Integer tgId;

    Long chatId;
}
