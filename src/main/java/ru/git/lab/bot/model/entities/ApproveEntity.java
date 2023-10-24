package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "approvals")
@NoArgsConstructor
public class ApproveEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "mr_id", nullable = false)
    Long mrId;

    @Column(name = "author_id", nullable = false)
    Long authorId;

    @Column(name = "author_name", nullable = false)
    String authorName;

    @Column(name = "is_delete", nullable = false)
    Boolean isDelete;
}
