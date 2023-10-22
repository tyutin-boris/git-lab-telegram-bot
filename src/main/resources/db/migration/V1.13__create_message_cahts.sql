CREATE TABLE IF NOT EXISTS message_chats
(
    id                BIGSERIAL NOT NULL PRIMARY KEY,
    tg_mr_messages_id BIGINT    NOT NULL,
    tg_id             INTEGER,
    chat_id           BIGINT    NOT NULL,

    CONSTRAINT tg_mr_message_fk
        FOREIGN KEY (tg_mr_messages_id)
            REFERENCES tg_mr_messages (id)
);

INSERT INTO message_chats (tg_mr_messages_id, tg_id, chat_id)
SELECT tgm.id, tgm.tg_id, tgm.chat_id
FROM tg_mr_messages AS tgm;
