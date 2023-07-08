package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    @Query("select new ru.git.lab.bot.dto.MessageToDelete(m.id, m.chatId, m.messageId) from MessageEntity m " +
            "where m.mrId = :mrId and m.authorEmail = :email and m.authorUsername = :username")
    List<MessageToDelete> getMessageToDelete(@Param("mrId") Long mrId,
                                             @Param("email") String email,
                                             @Param("username") String username);
}
