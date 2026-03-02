DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user"
(
    id          SERIAL PRIMARY KEY,
    gender      VARCHAR(20),
    firstname   VARCHAR(100),
    lastname    VARCHAR(100),
    civility    VARCHAR(20),
    email       VARCHAR(255),
    phone       VARCHAR(50),
    picture     VARCHAR(500),
    nationality VARCHAR(10)
);
