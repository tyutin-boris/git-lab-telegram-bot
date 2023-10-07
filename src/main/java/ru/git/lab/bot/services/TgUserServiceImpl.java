package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.mappers.TgUserMapper;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.TgUserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgUserServiceImpl implements TgUserService {

    private final TgUserMapper tgUserMapper;
    private final TgUserRepository tgUserRepository;

    @Override
    public void save(User user) {
        saveUser(user);
    }

    @Override
    public void saveUserIfNotExist(User user) {
        Long userId = user.getId();
        boolean existsById = tgUserRepository.existsById(userId);

        if (existsById) {
            log.debug("User with id: " + userId + "exist");
        } else {
            saveUser(user);
        }
    }

    private void saveUser(User user) {
        TgUserEntity tgUserEntity = tgUserMapper.toEntity(user);
        tgUserRepository.save(tgUserEntity);
        log.debug("Save tg user. id: " + user.getId() + ", username: " + user.getUserName());
    }
}
