package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.git.lab.bot.api.pipeline.PipelineStatus;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pipelines")
public class PipelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "mr_id", nullable = false)
    private Long mrId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PipelineStatus status;

    @Column(nullable = false)
    private String text;

    @Column(name = "create_date", nullable = false)
    private OffsetDateTime createDate;
}
