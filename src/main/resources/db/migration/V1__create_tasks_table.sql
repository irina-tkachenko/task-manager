CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       status VARCHAR(50) NOT NULL DEFAULT 'TODO',
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);