package ru.git.lab.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "approvals")
@NoArgsConstructor
public class ApproveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "mr_id")
    Long mrId;

    @Column(name = "author_id")
    Long authorId;

    @Column(name = "author_name")
    String authorName;
}
