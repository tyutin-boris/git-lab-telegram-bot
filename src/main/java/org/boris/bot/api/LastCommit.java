package org.boris.bot.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastCommit {
    Long id;
    String message;
    String title;
    LocalDateTime timestamp;
    String url;
    Author author;
}
