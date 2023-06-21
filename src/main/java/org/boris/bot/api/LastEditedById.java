package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LastEditedById {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime previous;
    Long current;
}