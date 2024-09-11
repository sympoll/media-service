-- Media Service Schema
CREATE TABLE images (
    id              SERIAL PRIMARY KEY,
    owner_id        VARCHAR(255),
    owner_type      VARCHAR(255),
    name            VARCHAR(255),
    type            VARCHAR(255),
    data            BYTEA,
    time_created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);