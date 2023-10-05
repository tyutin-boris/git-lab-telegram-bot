package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.mappers.TgUserMapper;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.services.api.TgUserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgUserServiceImpl implements TgUserService {

    private final TgUserMapper tgUserMapper;

    @Override
    public void save(User user) {
        TgUserEntity tgUserEntity = tgUserMapper.toEntity(user);
    }
}
