CREATE TABLE IF NOT EXISTS git_users (
    id VARCHAR NOT NULL PRIMARY KEY,
    git_id BIGINT NOT NULL,
    name VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    email VARCHAR NOT NULL
);