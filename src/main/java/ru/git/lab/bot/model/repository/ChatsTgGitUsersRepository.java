package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.TgGitUserId;

import java.util.List;

@Repository
public interface ChatsTgGitUsersRepository extends JpaRepository<ChatsTgGitUsersEntity, TgGitUserId> {
    List<ChatsTgGitUsersEntity> findAllByGitId(long gitId);

    boolean existsByGitIdAndTgIdAndChatId(Long gitId, Long tgId, Long chatId);
}
