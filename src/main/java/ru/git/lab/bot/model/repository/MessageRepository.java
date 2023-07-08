package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
