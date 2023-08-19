package ru.git.lab.bot.services.api;

import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.util.List;

public interface ApproveService {
    List<ApproveEntity> findAllByMrId(Long mrId);

    void save(Long mrId, User user);

    List<ApproveEntity> findAllByMrIdAndAuthorId(Long mrId, Long authorId);

    void deleteAll(List<ApproveEntity> approvalsForMr);
}
