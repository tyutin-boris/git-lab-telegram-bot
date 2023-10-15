package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.model.entities.ChatEntity;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("select id from ChatEntity")
    List<Long> getAllChatId();

//    Optional<ChatEntity> findByName(String name);

    List<ChatEntity> findAllByType(ChatType type);
}
