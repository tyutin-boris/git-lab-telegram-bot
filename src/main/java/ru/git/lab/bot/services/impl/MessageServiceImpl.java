package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.MessageService;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Override
    public void saveMessage(Message message, ObjectAttributes attributes) {
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();
        Long mrId = attributes.getId();
        Long author_id = attributes.getAuthorId();
    }
}