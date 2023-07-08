package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.User;

import java.util.Optional;

public class UserUtils {

    public static User getUser(MergeRequest request) {
        return Optional.ofNullable(request)
                .map(MergeRequest::getUser)
                .orElseThrow(() -> new RuntimeException("User not present"));
    }
}
