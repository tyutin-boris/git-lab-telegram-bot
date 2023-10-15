package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivateChatMessageRepository extends JpaRepository<PrivateChatMessageEntity, Long> {
    List<PrivateChatMessageEntity> findByTgUserId(Long tgUserId);

    @Query("SELECT pm FROM  PrivateChatMessageEntity pm " +
            "ORDER BY createDate DESC " +
            "LIMIT 1")
    Optional<PrivateChatMessageEntity> findLastMessageByTgUserId(Long tgUserId);

//    Optional<PrivateChatMessageEntity> findFirstByChatIdAndTgUserIdByDesc(Long chatId, Long tgUserId);
}
