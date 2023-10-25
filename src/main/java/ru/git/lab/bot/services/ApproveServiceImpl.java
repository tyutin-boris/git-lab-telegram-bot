package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.repository.ApproveRepository;
import ru.git.lab.bot.services.api.ApproveService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveServiceImpl implements ApproveService {

    private final ApproveRepository approveRepository;

    @Override
    public List<ApproveEntity> findAllByMrId(Long mrId) {
        return approveRepository.findAllByMrId(mrId);
    }

    @Override
    public void save(Long mrId, UserDto user) {
        ApproveEntity approveEntity = createApproveEntity(mrId, user);
        approveRepository.save(approveEntity);
    }

    @Override
    public List<ApproveEntity> findAllByMrIdAndAuthorId(Long mrId, Long authorId) {
        return approveRepository.findAllByMrIdAndAuthorId(mrId, authorId);
    }

    @Override
    public void deleteAll(List<ApproveEntity> approvals) {
        approveRepository.deleteAll(approvals);
    }

    @Override
    public List<ApproveEntity> findAllByMrIdAndIsDeleteFalse(Long mrId) {
        return approveRepository.findAllByMrIdAndIsDeleteFalse(mrId);
    }

    @Override
    public void saveAll(List<ApproveEntity> approvals) {
        approveRepository.saveAll(approvals);
    }

    private ApproveEntity createApproveEntity(Long mrId, UserDto user) {
        ApproveEntity approveEntity = new ApproveEntity();

        approveEntity.setMrId(mrId);
        approveEntity.setAuthorId(user.getId());
        approveEntity.setAuthorName(user.getName());
        return approveEntity;
    }
}
