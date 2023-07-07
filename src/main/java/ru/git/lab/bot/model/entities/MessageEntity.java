package ru.git.lab.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.OffsetDateTime;

//@Table
@Setter
@Getter
//@Entity(name = "message")
@NoArgsConstructor
public class MessageEntity {

    @Id
    Long id;

    @Column(name = "create_date_time")
    OffsetDateTime createDateTime;
}
