package ru.git.lab.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "git_id")
    private Long gitId;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;
}
