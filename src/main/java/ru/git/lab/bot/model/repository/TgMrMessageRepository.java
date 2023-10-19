package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TgMrMessageRepository extends JpaRepository<TgMrMessageEntity, Long> {

    @Query("SELECT new ru.git.lab.bot.dto.MessageToDelete(m.tgId, m.chatId) FROM TgMrMessageEntity m WHERE m.mrId = :mrId AND m.authorId = :authorId")
    List<MessageToDelete> getMessageToDelete(@Param("mrId") Long mrId, @Param("authorId") Long authorId);

    Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId);

    void deleteByTgId(Integer tgId);
}
