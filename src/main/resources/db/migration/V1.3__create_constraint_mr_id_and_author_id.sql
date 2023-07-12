ALTER TABLE messages ADD CONSTRAINT mr_id_and_author_id_uk UNIQUE(mr_id, author_id);
