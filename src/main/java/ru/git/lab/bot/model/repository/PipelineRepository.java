package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.PipelineEntity;

import java.util.Optional;

@Repository
public interface PipelineRepository extends JpaRepository<PipelineEntity, Long> {

    Optional<PipelineEntity> findTopByMrIdOrderByCreateDateDesc(Long mrId);
}
