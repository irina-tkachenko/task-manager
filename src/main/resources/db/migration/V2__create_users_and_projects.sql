CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

ALTER TABLE tasks ADD COLUMN project_id BIGINT REFERENCES projects(id);
ALTER TABLE tasks ADD COLUMN assignee_id BIGINT REFERENCES users(id);