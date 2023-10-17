package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;

import java.util.List;

@Repository
public interface ChatsTgGitUsersRepository extends JpaRepository<ChatsTgGitUsersEntity, Long> {
    List<ChatsTgGitUsersEntity> findAllByGitId(long gitId);

    boolean existsByTgIdAndChatId(Long tgId, Long chatId);

    boolean existsByGitIdAndTgIdAndChatId(Long gitId, Long tgId, Long chatId);
}
