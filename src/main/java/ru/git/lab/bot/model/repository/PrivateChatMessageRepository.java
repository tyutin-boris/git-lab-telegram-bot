package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivateChatMessageRepository extends JpaRepository<PrivateChatMessageEntity, Long> {
    List<PrivateChatMessageEntity> findByTgUserId(Long tgUserId);

//    @Query("SELECT pm FROM  PrivateChatMessageEntity pm " +
//            "WHERE pm.tgUserId = :tgUserId AND pm.chatId = :chatId " +
//            "ORDER BY createDate DESC " +
//            "LIMIT 1")
    Optional<PrivateChatMessageEntity> findByTgUserIdAndChatIdOrderByCreateDateDesc(
            @Param("tgUserId") Long tgUserId,
            @Param("chatId") Long chatId);

//    Optional<PrivateChatMessageEntity> findFirstByChatIdAndTgUserIdByDesc(Long chatId, Long tgUserId);
}
