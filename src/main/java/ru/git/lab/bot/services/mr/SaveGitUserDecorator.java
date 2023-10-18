package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.mappers.UserDtoMapper;
import ru.git.lab.bot.services.api.GitUserService;
import ru.git.lab.bot.services.api.MergeRequestService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveGitUserDecorator implements MergeRequestService {

    private final UserDtoMapper userDtoMapper;
    private final GitUserService gitUserService;
    private final MergeRequestService joinerTgAndGitUsersDecorator;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        log.debug("Try to save user if not exist");
        UserDto user = userDtoMapper.toDto(event.getUser());

        gitUserService.saveUserIfNotExist(user);

        joinerTgAndGitUsersDecorator.handleEvent(event);
    }
}
