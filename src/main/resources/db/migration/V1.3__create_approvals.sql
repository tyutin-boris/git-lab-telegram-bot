CREATE TABLE IF NOT EXISTS approvals
(
    id          BIGINT  NOT NULL PRIMARY KEY,
    mr_id       BIGINT  NOT NULL,
    author_id   BIGINT  NOT NULL,
    author_name VARCHAR NOT NULL
);
