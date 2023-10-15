package ru.git.lab.bot.dto;

import lombok.Data;

@Data
public class KeyboardButton {
    private String callbackData;
    private String text;
}
