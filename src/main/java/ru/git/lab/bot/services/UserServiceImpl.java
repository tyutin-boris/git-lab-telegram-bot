package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.mappers.UserMapper;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.repository.UserRepository;
import ru.git.lab.bot.services.api.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void saveUserIfNotExist(User user) {
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    if (!userRepository.existsByGitIdAndUsernameAndEmail(u.getId(), u.getUsername(), u.getEmail())) {
                        GitUserEntity gitUserEntity = userMapper.toEntity(user);
                        userRepository.save(gitUserEntity);
                    }
                });
    }

    @Override
    public GitUserEntity getByAuthorId(Long id) {
        return userRepository.findByGitId(id).orElseThrow(() -> new RuntimeException("User not found id: " + id));
    }
}
