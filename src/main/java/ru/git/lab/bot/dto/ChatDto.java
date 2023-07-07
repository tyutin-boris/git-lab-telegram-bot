package ru.git.lab.bot.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ChatDto {
    private Long id;
    private String type;
    private String title;
    private OffsetDateTime createDate;
}
