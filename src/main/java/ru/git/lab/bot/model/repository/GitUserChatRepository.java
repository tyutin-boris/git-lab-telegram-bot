package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.ChatGitUserEntity;

import java.util.List;

@Repository
public interface GitUserChatRepository extends JpaRepository<ChatGitUserEntity, Long> {

    List<Long> findChatIdByGitUserId(long gitUserId);
}
