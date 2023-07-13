package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;

import java.util.Optional;

public class ObjectAttributesUtils {

    public static ObjectAttributes getObjectAttributes(MergeRequestEvent event) {
        return Optional.of(event)
                .map(MergeRequestEvent::getObjectAttributes)
                .orElseThrow(() -> new RuntimeException("Merge event without object attributes"));
    }

    public static Action getAction(ObjectAttributes objectAttributes) {
        return Optional.ofNullable(objectAttributes.getAction())
                .orElse(Action.INDEFINITELY);
    }
}
