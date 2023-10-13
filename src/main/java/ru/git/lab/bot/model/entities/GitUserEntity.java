package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "git_users")
@NoArgsConstructor
public class GitUserEntity {

    @Id
    @Column(name = "git_id")
    private Long gitId;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "tg_id")
    private Long tgId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chats", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<ChatEntity> chats;
}
