package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.model.entities.MergeRequestHistoryEntity;
import ru.git.lab.bot.model.repository.MergeRequestHistoryRepository;
import ru.git.lab.bot.services.mr.api.MergeRequestHistoryService;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestHistoryServiceImpl implements MergeRequestHistoryService {

    private final MergeRequestHistoryRepository mergeRequestHistoryRepository;

    @Override
    public void save(String message) {
        MergeRequestHistoryEntity mergeRequestHistoryEntity = new MergeRequestHistoryEntity();
        mergeRequestHistoryEntity.setMessage(message);
        mergeRequestHistoryEntity.setCreateDate(OffsetDateTime.now());

        mergeRequestHistoryRepository.save(mergeRequestHistoryEntity);
        log.debug("Save MR history");
    }
}
