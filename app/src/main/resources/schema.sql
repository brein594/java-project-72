DROP TABLE IF EXISTS url_checks CASCADE;
DROP TABLE IF EXISTS urls CASCADE;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    status_code INT NOT NULL,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description TEXT,
    url_id BIGINT NOT NULL REFERENCES urls(id) ON DELETE CASCADE,
    created_at TIMESTAMP
);