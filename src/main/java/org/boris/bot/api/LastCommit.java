package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LastCommit {
    String id;
    String message;
    String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime timestamp;
    String url;
    Author author;
}
