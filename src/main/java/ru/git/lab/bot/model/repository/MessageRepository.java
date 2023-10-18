package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT m.id, m.chatId FROM MessageEntity m WHERE m.mrId = :mrId AND m.authorId = :authorId")
    List<MessageToDelete> getMessageToDelete(@Param("mrId") Long mrId, @Param("authorId") Long authorId);

    Optional<MessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId);
}
