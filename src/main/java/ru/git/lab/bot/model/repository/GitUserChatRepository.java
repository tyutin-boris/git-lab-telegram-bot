package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.ChatGitUserEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface GitUserChatRepository extends JpaRepository<ChatGitUserEntity, Long> {

    List<Long> findChatIdByGitUserId(long gitUserId);

    Optional<GitUserEntity> findByUsername(String username);
}
