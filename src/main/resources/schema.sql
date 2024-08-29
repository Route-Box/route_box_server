DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS inquiry_response;
DROP TABLE IF EXISTS inquiry_image;
DROP TABLE IF EXISTS inquiry;
DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS user_profile_image;
DROP TABLE IF EXISTS user_point_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS route_activities;
DROP TABLE IF EXISTS route_activity_image;
DROP TABLE IF EXISTS route_points;
DROP TABLE IF EXISTS purchased_route;
DROP TABLE IF EXISTS user_report;
DROP TABLE IF EXISTS route_report;

CREATE TABLE users
(
    user_id                         BIGINT       NOT NULL AUTO_INCREMENT,
    roles                           VARCHAR(255) NOT NULL,
    login_type                      VARCHAR(255) NOT NULL,
    social_login_uid                VARCHAR(255) NOT NULL UNIQUE,
    profile_image_url               VARCHAR(255) NOT NULL,
    nickname                        VARCHAR(8)   NOT NULL UNIQUE,
    point                           INT          NOT NULL,
    gender                          VARCHAR(255) NOT NULL,
    birth_day                       DATE         NOT NULL,
    introduction                    VARCHAR(25)  NOT NULL,
    enable_receiving_marketing_info BOOLEAN      NOT NULL,
    enable_receiving_travel_photo   BOOLEAN      NOT NULL,
    created_at                      DATETIME     NOT NULL,
    updated_at                      DATETIME     NOT NULL,
    deleted_at                      DATETIME,
    PRIMARY KEY (user_id)
);

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

CREATE TABLE coupon
(
    coupon_id  BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(255) NOT NULL,
    type       VARCHAR(10)  NOT NULL,
    status     VARCHAR(10)  NOT NULL,
    started_at DATETIME     NOT NULL COMMENT '쿠폰 이용 시작 시각',
    ended_at   DATETIME COMMENT '쿠폰 이용 종료 시각. NULL인 경우 무제한',
    expired_at DATETIME COMMENT '쿠폰 만료 시각',
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (coupon_id)
);
CREATE INDEX idx__coupon__user_id ON coupon (user_id);

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

CREATE TABLE routes
(
    route_id         BIGINT AUTO_INCREMENT,
    user_id          BIGINT,
    name             VARCHAR(255),
    description      TEXT,
    start_time       DATETIME,
    end_time         DATETIME,
    who_with         VARCHAR(255),
    number_of_people INT,
    number_of_days   VARCHAR(255),
    style            JSON,
    transportation   VARCHAR(255),
    is_public        BOOLEAN  NOT NULL DEFAULT FALSE,
    created_at       DATETIME NOT NULL,
    updated_at       DATETIME NOT NULL,
    PRIMARY KEY (route_id)
);
CREATE INDEX idx__routes__user_id ON routes (user_id);

CREATE TABLE route_activities
(
    route_activity_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id          BIGINT       NOT NULL,
    location_name     VARCHAR(255) NOT NULL,
    address           VARCHAR(255) NOT NULL,
    latitude          VARCHAR(255) NULL,
    longitude         VARCHAR(255) NULL,
    visit_date        DATE         NOT NULL,
    start_time        TIME         NOT NULL,
    end_time          TIME         NOT NULL,
    category          VARCHAR(100) NOT NULL,
    description       TEXT NULL,
    created_at        DATETIME     NOT NULL,
    updated_at        DATETIME     NOT NULL
);
CREATE INDEX idx__route_activities__route_id ON route_activities (route_id);

CREATE TABLE route_activity_image
(
    route_activity_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_activity_id       BIGINT       NOT NULL,
    stored_file_name        VARCHAR(255) NOT NULL,
    file_url                VARCHAR(255) NOT NULL,
    created_at              DATETIME     NOT NULL,
    updated_at              DATETIME     NOT NULL,
    deleted_at              DATETIME NULL
);
CREATE INDEX idx__route_activity_image__route_activity_id ON route_activity_image (route_activity_id);

CREATE TABLE route_points
(
    point_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id    BIGINT       NOT NULL,
    latitude    VARCHAR(255) NOT NULL,
    longitude   VARCHAR(255) NOT NULL,
    point_order INT          NOT NULL,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME     NOT NULL
);
CREATE INDEX idx__route_points__route_id ON route_points (route_id);

CREATE TABLE purchased_route
(
    purchased_route_id BIGINT   NOT NULL AUTO_INCREMENT,
    route_id           BIGINT   NOT NULL,
    buyer_id           BIGINT   NOT NULL,
    writer_id          BIGINT   NOT NULL,
    name               VARCHAR(255),
    description        TEXT,
    start_time         DATETIME NOT NULL,
    end_time           DATETIME NOT NULL,
    who_with           VARCHAR(255),
    number_of_peoples  INT,
    number_of_days     VARCHAR(255),
    styles             JSON     NOT NULL,
    transportation     VARCHAR(255),
    created_at         DATETIME NOT NULL,
    updated_at         DATETIME NOT NULL,
    created_by         BIGINT   NOT NULL,
    updated_by         BIGINT   NOT NULL,
    PRIMARY KEY (purchased_route_id)
);
CREATE INDEX idx__routes__route_id ON purchased_route (route_id);
CREATE INDEX idx__routes__buyer_id ON purchased_route (buyer_id);
CREATE INDEX idx__routes__writer_id ON purchased_route (writer_id);

CREATE TABLE user_report
(
    user_report_id   BIGINT   NOT NULL AUTO_INCREMENT,
    reporter_id      BIGINT   NOT NULL COMMENT '신고자 id',
    reported_user_id BIGINT   NOT NULL COMMENT '신고 대상(유저)의 id',
    created_at       DATETIME NOT NULL,
    updated_at       DATETIME NOT NULL,
    PRIMARY KEY (user_report_id)
);
-- CREATE INDEX idx__user_report__reporter_id ON user_report (reporter_id);
-- CREATE INDEX idx__user_report__reported_user_id ON user_report (reported_user_id);

CREATE TABLE route_report
(
    route_report_id   BIGINT       NOT NULL AUTO_INCREMENT,
    reporter_id       BIGINT       NOT NULL COMMENT '신고자 id',
    reported_route_id BIGINT       NOT NULL COMMENT '신고 대상(루트)의 id',
    reason_type       VARCHAR(255) NOT NULL COMMENT '신고 사유',
    reason_detail     VARCHAR(255) NOT NULL COMMENT '신고 상세 사유',
    created_at        DATETIME     NOT NULL,
    updated_at        DATETIME     NOT NULL,
    PRIMARY KEY (route_report_id)
)
-- CREATE INDEX idx__route_report__reporter_id ON route_report (reporter_id);
-- CREATE INDEX idx__route_report__reported_route_id ON route_report (reported_route_id);
