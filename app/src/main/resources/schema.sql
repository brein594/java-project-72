DROP TABLE  IF EXISTS urls CASCADE;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

DROP TABLE IF EXISTS url_checks CASCADE;

 CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    status_code INT NOT NULL,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description TEXT,
    url_id BIGINT REFERENCES urls(id) NOT NULL,
    created_at TIMESTAMP
);
