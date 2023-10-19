package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.mappers.UserMapper;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.repository.GitUserRepository;
import ru.git.lab.bot.services.api.GitUserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitUserServiceImpl implements GitUserService {

    private final UserMapper userMapper;

    private final GitUserRepository gitUserRepository;

    @Override
    public void saveUserIfNotExist(UserDto user) {
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    Long gitId = u.getId();
                    String username = u.getUsername();
                    if (!gitUserRepository.existsByIdAndUsernameAndEmail(gitId, username, u.getEmail())) {
                        log.debug("Try save git user; gitId: {}, username: {}", gitId, username);
                        GitUserEntity entity = userMapper.toEntity(user);
                        gitUserRepository.save(entity);
                        log.debug("Save user with id: {}, username: {}", entity.getId(), entity.getUsername());
                    }
                });
    }

    @Override
    public GitUserEntity getByAuthorId(Long id) {
        return gitUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found id: " + id));
    }
}
