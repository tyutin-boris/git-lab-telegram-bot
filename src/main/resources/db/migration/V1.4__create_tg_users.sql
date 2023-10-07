CREATE TABLE IF NOT EXISTS tg_users (
    id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR,
    username VARCHAR NOT NULL
);
