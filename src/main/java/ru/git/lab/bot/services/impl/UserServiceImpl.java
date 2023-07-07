package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.mappers.UserMapper;
import ru.git.lab.bot.model.entities.UserEntity;
import ru.git.lab.bot.model.repository.UserRepository;
import ru.git.lab.bot.services.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    UserEntity userEntity = userMapper.toEntity(user);
                    userRepository.save(userEntity);
                });
    }
}
