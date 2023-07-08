package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("select chatId, messageId from MessageEntity m" +
            "where m.mrId = :mrId and m.authorEmail = :email and m.authorUsername = :username")
    MessageToDelete getMessageToDelete(@Param("mrId") Long mrId,
                                       @Param("email") String email,
                                       @Param("username") String username);
}
