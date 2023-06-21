package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UpdatedAt {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime previous;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime current;
}
