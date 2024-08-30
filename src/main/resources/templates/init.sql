-- Media Service Schema
CREATE TABLE images (
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    data    BYTEA
);