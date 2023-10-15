package ru.git.lab.bot.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatResponse {

    private Long chatId;
    private String text;
    private List<KeyboardButton> buttons;
}
