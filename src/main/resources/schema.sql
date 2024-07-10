DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    user_id    BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL, -- TODO: UNIQUE 정책 확인 필요
    gender     VARCHAR(10)  NOT NULL, -- MALE, FEMALE, PRIVATE
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (user_id)
);
CREATE INDEX idx__users__name ON users (name);
