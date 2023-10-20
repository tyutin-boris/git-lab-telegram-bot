package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.mr.api.CloseMrService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloseMrServiceImpl implements CloseMrService {

    private final TgMrMessageService messageService;

    @Override
    public void deleteMessage(Long mrId) {
        Optional<TgMrMessageEntity> message = messageService.findByMrId(mrId);

        if (message.isPresent()) {
            messageService.delete(message.get());
        } else {
            log.error("Message to delete not found. mrId: {}", mrId);
        }
    }
}
