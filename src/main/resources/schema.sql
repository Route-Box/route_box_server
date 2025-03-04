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
DROP TABLE IF EXISTS withdrawal_histories;
DROP TABLE IF EXISTS popular_routes;
DROP TABLE IF EXISTS recommended_routes;
DROP TABLE IF EXISTS comments;

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
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    user_id          BIGINT       NOT NULL,
    route_id         BIGINT,
    transaction_type VARCHAR(255) NOT NULL,
    amount           INT          NOT NULL,
    created_at       DATETIME     NOT NULL,
    updated_at       DATETIME     NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX idx__user_point_history__user_id ON user_point_history (user_id);
CREATE INDEX idx__user_point_history__route_id ON user_point_history (route_id);

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
    content    VARCHAR(500) NOT NULL,
    reply      VARCHAR(500) NULL,
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
    route_id           BIGINT AUTO_INCREMENT,
    user_id            BIGINT,
    name               VARCHAR(255),
    description        TEXT,
    start_time         DATETIME,
    end_time           DATETIME,
    who_with           VARCHAR(255),
    number_of_people   INT,
    number_of_days     VARCHAR(255),
    style              JSON,
    styles             VARCHAR(255),
    transportation     VARCHAR(255),
    is_public          BOOLEAN  NOT NULL DEFAULT FALSE,
    record_finished_at DATETIME,
    created_at         DATETIME NOT NULL,
    updated_at         DATETIME NOT NULL,
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
    description       TEXT         NULL,
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
    deleted_at              DATETIME     NULL
);
CREATE INDEX idx__route_activity_image__route_activity_id ON route_activity_image (route_activity_id);

CREATE TABLE route_points
(
    point_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id   BIGINT       NOT NULL,
    latitude   VARCHAR(255) NOT NULL,
    longitude  VARCHAR(255) NOT NULL,
    record_at  DATETIME     NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
);
CREATE INDEX idx__route_points__route_id ON route_points (route_id);

CREATE TABLE purchased_route
(
    purchased_route_id BIGINT       NOT NULL AUTO_INCREMENT,
    route_id           BIGINT       NOT NULL,
    buyer_id           BIGINT       NOT NULL,
    writer_id          BIGINT       NOT NULL,
    name               VARCHAR(255),
    description        TEXT,
    start_time         DATETIME     NOT NULL,
    end_time           DATETIME     NOT NULL,
    who_with           VARCHAR(255),
    number_of_peoples  INT,
    number_of_days     VARCHAR(255),
    styles             VARCHAR(255) NOT NULL,
    transportation     VARCHAR(255),
    route_points       JSON         NOT NULL,
    route_activities   JSON         NOT NULL,
    created_at         DATETIME     NOT NULL,
    updated_at         DATETIME     NOT NULL,
    created_by         BIGINT       NOT NULL,
    updated_by         BIGINT       NOT NULL,
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
    reported_route_id BIGINT       NOT NULL COMMENT '신고 대상(루트)의 id고',
    reason_types      VARCHAR(255) NOT NULL COMMENT '신고 사유',
    reason_detail     VARCHAR(255) COMMENT '신고 상세 사유',
    created_at        DATETIME     NOT NULL,
    updated_at        DATETIME     NOT NULL,
    PRIMARY KEY (route_report_id)
);
-- CREATE INDEX idx__route_report__reporter_id ON route_report (reporter_id);
-- CREATE INDEX idx__route_report__reported_route_id ON route_report (reported_route_id);

CREATE TABLE withdrawal_histories
(
    withdrawal_history_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '탈퇴 기록 ID',
    user_id               BIGINT        NOT NULL COMMENT '사용자 ID',
    reason_type           VARCHAR(255)  NULL COMMENT '탈퇴 사유',
    reason_detail         VARCHAR(1600) NULL COMMENT '탈퇴 상세 사유',
    created_at            DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at            DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
);

CREATE TABLE popular_routes
(
    popular_route_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '인기 루트 ID',
    route_id         BIGINT   NOT NULL COMMENT '루트 ID',
    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
);

CREATE TABLE recommended_routes
(
    recommended_route_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '추천 루트 ID',
    route_id             BIGINT       NOT NULL COMMENT '루트 ID',
    show_from            DATETIME     NOT NULL COMMENT '표시 시작 시간',
    common_comment       VARCHAR(255) NULL COMMENT '추천 루트 상단에 나오는 공통 코멘트, 가장 앞쪽 데이터의 코멘트가 노출됨',
    created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
);

CREATE TABLE comments (
    comment_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 ID',
    route_id BIGINT NOT NULL COMMENT '댓글이 달리는 루트(게시글) ID',
    user_id BIGINT NOT NULL COMMENT '댓글 작성자 ID',
    content VARCHAR(500) NOT NULL COMMENT '댓글 내용',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
);
CREATE INDEX idx__comments__route_id ON comments (route_id);
CREATE INDEX idx__comments__user_id ON comments (user_id);

CREATE TABLE comment_report (
    comment_report_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 신고 ID',
    reporter_id BIGINT NOT NULL COMMENT '신고자 ID',
    reported_comment_id BIGINT NOT NULL COMMENT '신고된 댓글 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
);
-- CREATE INDEX idx__comment_report__reporter_id ON comment_report (reporter_id);
-- CREATE INDEX idx__comment_report__reported_comment_id ON comment_report (reported_comment_id);
