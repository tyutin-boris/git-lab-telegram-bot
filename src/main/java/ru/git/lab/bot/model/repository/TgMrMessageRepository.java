package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;

import java.util.Optional;

@Repository
public interface TgMrMessageRepository extends JpaRepository<TgMrMessageEntity, Long> {

    Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId);

    Optional<TgMrMessageEntity> findByMrId(Long mrId);
}
