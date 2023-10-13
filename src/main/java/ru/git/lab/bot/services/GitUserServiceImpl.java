package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.mappers.UserMapper;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.repository.GetUserRepository;
import ru.git.lab.bot.services.api.GitUserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitUserServiceImpl implements GitUserService {

    private final UserMapper userMapper;

    private final GetUserRepository getUserRepository;

    @Override
    public void saveUserIfNotExist(UserDto user) {
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    if (!getUserRepository.existsByGitIdAndUsernameAndEmail(u.getId(), u.getUsername(), u.getEmail())) {
                        GitUserEntity gitUserEntity = userMapper.toEntity(user);
                        getUserRepository.save(gitUserEntity);
                    }
                });
    }

    @Override
    public GitUserEntity getByAuthorId(Long id) {
        return getUserRepository.findByGitId(id)
                .orElseThrow(() -> new RuntimeException("User not found id: " + id));
    }
}
