DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS notification_settings;
DROP TABLE IF EXISTS inquiry_response;
DROP TABLE IF EXISTS inquiry_image;
DROP TABLE IF EXISTS inquiry;
DROP TABLE IF EXISTS user_profile_image;
DROP TABLE IF EXISTS user_point_history;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id           BIGINT       NOT NULL AUTO_INCREMENT,
    login_type        VARCHAR(255) NOT NULL,
    social_login_uid  VARCHAR(255) NOT NULL UNIQUE,
    profile_image_url VARCHAR(255) NOT NULL,
    nickname          VARCHAR(8)   NOT NULL UNIQUE,
    point             INT          NOT NULL,
    gender            VARCHAR(255) NOT NULL,
    birth_day         DATE         NOT NULL,
    introduction      VARCHAR(25)  NOT NULL,
    created_at        DATETIME     NOT NULL,
    updated_at        DATETIME     NOT NULL,
    deleted_at        DATETIME,
    PRIMARY KEY (user_id)
);
-- CREATE UNIQUE INDEX idx__users__nickname ON users (nickname);

CREATE TABLE user_profile_image
(
    user_profile_image_id BIGINT       NOT NULL AUTO_INCREMENT,
    user_id               BIGINT       NOT NULL,
    stored_file_name      VARCHAR(255) NOT NULL UNIQUE,
    file_url              VARCHAR(255) NOT NULL UNIQUE,
    created_at            DATETIME     NOT NULL,
    updated_at            DATETIME     NOT NULL,
    deleted_at            DATETIME,
    PRIMARY KEY (user_profile_image_id)
);
CREATE INDEX idx__user_profile_image__user_id ON user_profile_image (user_id);

CREATE TABLE user_point_history
(
    user_point_history_id BIGINT       NOT NULL AUTO_INCREMENT,
    user_id               BIGINT       NOT NULL,
    transaction_type      VARCHAR(255) NOT NULL,
    amount                INT          NOT NULL,
    created_at            DATETIME     NOT NULL,
    updated_at            DATETIME     NOT NULL,
    PRIMARY KEY (user_point_history_id)
);
CREATE INDEX idx__user_point_history__user_id ON user_point_history (user_id);

CREATE TABLE notification
(
    notification_id BIGINT       NOT NULL AUTO_INCREMENT,
    user_id         BIGINT       NOT NULL,
    is_read         BOOLEAN      NOT NULL,
    content         VARCHAR(255) NOT NULL,
    created_at      DATETIME     NOT NULL,
    updated_at      DATETIME     NOT NULL,
    PRIMARY KEY (notification_id)
);
CREATE INDEX idx__notification__user_id ON notification (user_id);

CREATE TABLE notification_settings
(
    notification_settings_id BIGINT   NOT NULL AUTO_INCREMENT,
    user_id                  BIGINT   NOT NULL,
    receive_marketing_info   BOOLEAN  NOT NULL,
    receive_travel_photo     BOOLEAN  NOT NULL,
    created_at               DATETIME NOT NULL,
    updated_at               DATETIME NOT NULL,
    PRIMARY KEY (notification_settings_id)
);
CREATE INDEX idx__notification_settings__user_id ON notification_settings (user_id);

CREATE TABLE inquiry
(
    inquiry_id BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,
    type       VARCHAR(255) NOT NULL,
    content    VARCHAR(400) NOT NULL,
    status     VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (inquiry_id)
);
CREATE INDEX idx__inquiry__user_id ON inquiry (user_id);

CREATE TABLE inquiry_image
(
    inquiry_image_id BIGINT       NOT NULL AUTO_INCREMENT,
    inquiry_id       BIGINT       NOT NULL,
    stored_file_name VARCHAR(255) NOT NULL,
    file_url         VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL,
    updated_at       DATETIME     NOT NULL,
    PRIMARY KEY (inquiry_image_id)
);
CREATE INDEX idx__inquiry_image__inquiry_id ON inquiry_image (inquiry_id);

CREATE TABLE inquiry_response
(
    inquiry_response_id BIGINT       NOT NULL AUTO_INCREMENT,
    inquiry_id          BIGINT       NOT NULL,
    content             VARCHAR(255) NOT NULL,
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME     NOT NULL,
    PRIMARY KEY (inquiry_response_id)
);
CREATE INDEX idx__inquiry_response__inquiry_id ON inquiry_response (inquiry_id);
