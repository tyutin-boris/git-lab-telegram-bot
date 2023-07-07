package ru.git.lab.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UpdatedById {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime previous;
    Long current;
}
