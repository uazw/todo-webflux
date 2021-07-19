CREATE TABLE IF NOT EXISTS task (
    task_id BIGSERIAL NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (task_id)
);