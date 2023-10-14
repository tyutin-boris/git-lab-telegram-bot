CREATE TABLE IF NOT EXISTS messages
(
    id               BIGINT    NOT NULL,
    chat_id          BIGINT    NOT NULL,
    mr_id            BIGINT    NOT NULL,
    author_id        BIGINT    NOT NULL,
    create_date_time TIMESTAMP NOT NULL
);

ALTER TABLE messages
    ADD CONSTRAINT mr_id_and_author_id_uk UNIQUE (mr_id, author_id);
