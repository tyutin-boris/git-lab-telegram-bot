package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.mappers.UserDtoMapper;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.TgGitUsersEntity;
import ru.git.lab.bot.model.repository.ChatsTgGitUsersRepository;
import ru.git.lab.bot.model.repository.TgGitUsersRepository;
import ru.git.lab.bot.services.api.MergeRequestService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinerTgAndGitUsersDecorator implements MergeRequestService {

    private final UserDtoMapper userDtoMapper;

    private final TgGitUsersRepository tgGitUsersRepository;

    private final MergeRequestService mergeRequestHistoryDecorator;

    private final ChatsTgGitUsersRepository chatsTgGitUsersRepository;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        log.debug("Try to join git and tg accounts");
        UserDto dto = userDtoMapper.toDto(event.getUser());

        tgGitUsersRepository.findByGitUsername(dto.getUsername())
                .ifPresent(user -> {
                    log.debug("Founded user by tg id: {}", user.getTgId());
                    Long gitId = dto.getId();
                    joinGitToTg(user, gitId);
                    joinToTgChat(user, gitId);
                });

        mergeRequestHistoryDecorator.handleEvent(event);
    }

    private void joinGitToTg(TgGitUsersEntity user, Long gitId) {
        if (Objects.isNull(user.getGitId())) {
            user.setGitId(gitId);
            tgGitUsersRepository.save(user);
            log.debug("Save tg git user with git id: {}, tg id: {}, username: {}", gitId, user.getTgId(), user.getGitUsername());
        }
    }

    private void joinToTgChat(TgGitUsersEntity user, Long gitId) {
        Long tgId = user.getTgId();
        Optional<ChatsTgGitUsersEntity> byTgId = chatsTgGitUsersRepository.findAllByTgId(tgId)
                .stream()
                .findFirst();
        byTgId.ifPresent(e -> {
            if (Objects.isNull(e.getGitId())) {
                e.setGitId(gitId);
                chatsTgGitUsersRepository.save(e);
                log.debug("Save chat tg git user with git id: {}, tg id: {}, for chat id: {}", gitId, tgId, e.getChatId());
            }
        });
    }
}
