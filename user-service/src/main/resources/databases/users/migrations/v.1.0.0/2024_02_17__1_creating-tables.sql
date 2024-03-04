CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    email     VARCHAR(100) NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname  VARCHAR(100) NOT NULL,
    role      VARCHAR(100) NOT NULL
);
