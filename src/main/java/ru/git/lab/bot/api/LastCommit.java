package ru.git.lab.bot.api;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LastCommit {
    String id;
    String message;
    String title;
    OffsetDateTime timestamp;
    String url;
    Author author;
}
