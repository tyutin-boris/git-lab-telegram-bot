package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.mappers.TgUserMapper;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.TgUserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgUserServiceImpl implements TgUserService {

    private final TgUserMapper tgUserMapper;
    private final TgUserRepository tgUserRepository;

    @Override
    public void save(User user) {
        Optional.ofNullable(user).map(this::saveUser)
                .orElseThrow(() -> new RuntimeException("User no save because is is null"));
    }

    @Override
    public void saveUserIfNotExist(User user) {
        boolean existsById = Optional.ofNullable(user)
                .map(User::getId)
                .map(tgUserRepository::existsById)
                .orElseThrow(() -> new RuntimeException("User no save because is is null"));

        if (existsById) {
            log.debug("User with id: " + user.getId() + "exist");
        } else {
            saveUser(user);
        }
    }

    private Long saveUser(User user) {
        TgUserEntity tgUserEntity = tgUserMapper.toEntity(user);
        tgUserRepository.save(tgUserEntity);

        Long userId = user.getId();
        log.debug("Save tg user. id: " + userId + ", username: " + user.getUserName());
        return userId;
    }
}
