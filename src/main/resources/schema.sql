CREATE TABLE IF NOT EXISTS user_entity (
    id SERIAL PRIMARY KEY,
    gender VARCHAR(10),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    civility VARCHAR(20),
    email VARCHAR(255),
    phone VARCHAR(50),
    picture VARCHAR(255),
    nat VARCHAR(10)
);
