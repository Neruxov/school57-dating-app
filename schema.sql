-- Создание таблицы для хранения реакций пользователей на анкеты других
CREATE TABLE user_reactions
(
    id        BIGINT  NOT NULL,
    reaction  BOOLEAN NOT NULL, -- TRUE - лайк, FALSE - дизлайк
    target_id BIGINT  NOT NULL, -- Тот, на кого была оставлена реакция
    user_id   BIGINT  NOT NULL, -- Тот, кто оставил реакцию
    PRIMARY KEY (id)
);

-- Создание таблицы для хранения пользователей
CREATE TABLE users
(
    id            BIGINT  NOT NULL,
    age           INTEGER NOT NULL,
    first_name    VARCHAR(255),
    gender        VARCHAR(10) CHECK (gender IN ('FEMALE', 'MALE')),
    last_name     VARCHAR(255),
    login         VARCHAR(255),
    password_hash VARCHAR(255),
    photo         VARCHAR(255), -- URL фотографии
    token         VARCHAR(255), -- Токен для авторизации
    PRIMARY KEY (id)
);

-- Автоинкремент для таблицы user_reactions
CREATE SEQUENCE user_reactions_seq
    START WITH 1
    INCREMENT BY 50;

-- Автоинкремент для таблицы users
CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 50;
