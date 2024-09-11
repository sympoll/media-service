-- Media Service Schema
CREATE TABLE images (
    id              SERIAL PRIMARY KEY,
    owner_id        UUID,
    name            VARCHAR(255),
    data            BYTEA,
    time_created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);