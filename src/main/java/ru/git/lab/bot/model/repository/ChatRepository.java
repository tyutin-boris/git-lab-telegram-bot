package ru.git.lab.bot.model.repository;

import ru.git.lab.bot.model.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("select id from ChatEntity")
    List<Long> getAllId();

}
