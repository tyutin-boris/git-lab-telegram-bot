CREATE TABLE IF NOT EXISTS messages (
    id VARCHAR NOT NULL PRIMARY KEY,
    message_id INTEGER NOT NULL,
    chat_id BIGINT NOT NULL,
    mr_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    author_email VARCHAR NOT NULL,
    author_username VARCHAR NOT NULL,
    create_date_time TIMESTAMP NOT NULL
);
