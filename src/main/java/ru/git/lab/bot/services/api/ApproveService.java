package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.util.List;

public interface ApproveService {
    List<ApproveEntity> findAllByMrId(Long mrId);

    void save(Long mrId, UserDto user);

    List<ApproveEntity> findAllByMrIdAndAuthorId(Long mrId, Long authorId);

    void deleteAll(List<ApproveEntity> approvalsForMr);

    List<ApproveEntity> findAllByMrIdAndIsDeleteFalse(Long mrId);
}
