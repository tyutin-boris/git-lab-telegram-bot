CREATE TABLE IF NOT EXISTS tg_mr_messages
(
    id               BIGSERIAL NOT NULL PRIMARY KEY,
    tg_id            INTEGER,
    chat_id          BIGINT    NOT NULL,
    mr_id            BIGINT    NOT NULL,
    author_id        BIGINT    NOT NULL,
    create_date_time TIMESTAMP NOT NULL
);

INSERT INTO tg_mr_messages (tg_id, chat_id, mr_id, author_id, create_date_time)
SELECT m.id, m.chat_id, m.mr_id, m.author_id, m.create_date_time
FROM messages AS m;

DROP TABLE IF EXISTS messages;
