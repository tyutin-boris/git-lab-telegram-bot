package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApproveRepository extends JpaRepository<ApproveEntity, UUID> {

    List<ApproveEntity> findAllByMrId(Long mrId);

    List<ApproveEntity> findAllByMrIdAndAuthorId(Long mrId, Long authorId);

    List<ApproveEntity> findAllByMrIdAndIsDeleteFalse(Long mrId);
}
