package com.routebox.routebox.exception

/**
 * Error code 목록.
 * Error code 값은 각 도메인(또는 기능)별로 **200** 단위씩 끊어서 정의한다.
 *
 * - 2XXX: 일반 예외. 아래 항목에 해당하지 않는 대부분의 예외가 여기에 해당한다.
 * - 3000 ~ 3199: 유저 관련 예외
 * - 3200 ~ 3399: 루트 관련 예외
 * - 3400 ~ 3599: 담은(구매한) 루트 관련 예외
 * - 3600 ~ 3799: 댓글 관련 예외
 * - 10000 ~ 10199: kakao(kakao open api 등) 관련 관련 예외
 */
enum class CustomExceptionType(
    val code: Int,
    val message: String,
) {
    ACCESS_DENIED(2000, "접근이 거부되었습니다."),
    UNAUTHORIZED(2001, "유효하지 않은 인증 정보로 인해 인증 과정에서 문제가 발생하였습니다."),
    INVALID_TOKEN(2002, "유효하지 않은 토큰입니다. 토큰 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다."),

    /**
     * 유저 관련 예외
     */
    USER_NOT_FOUND(3000, "일치하는 유저를 찾을 수 없습니다."),
    USER_SOCIAL_LOGIN_UID_DUPLICATION(3001, "이미 가입된 계정입니다."),
    USER_NICKNAME_DUPLICATION(3002, "이미 사용중인 닉네임입니다."),
    NO_AVAILABLE_COUPON(3003, "이용 가능한 쿠폰이 없습니다."),
    USER_WITHDRAWN(3004, "탈퇴한 유저입니다."),

    /**
     * 루트 관련 예외
     */
    ROUTE_NOT_FOUND(3200, "일치하는 루트 정보를 찾을 수 없습니다."),

    /**
     * 담은(구매한) 루트 관련 예외
     */
    PURCHASED_ROUTE_NOT_FOUND(3400, "일치하는 구매한 루트 정보를 찾을 수 없습니다."),
    ROUTE_NOT_PURCHASED(3401, "내가 구매하지 않은 루트이거나, 유효하지 않은 구매 루트입니다."),

    /**
     * 문의 관련 예외
     */
    INQUIRY_NOT_FOUND(3500, "일치하는 문의 내역을 찾을 수 없습니다."),

    /**
     * Kakao(kakao open api 등) 관련 예외
     */
    REQUEST_KAKAO_USER_INFO(10000, "카카오 사용자 정보 조회 중 오류가 발생했습니다."),

    /**
     * 댓글 관련 예외
     */
    COMMENT_NOT_FOUND(3600, "일치하는 댓글을 찾을 수 없습니다."),
    COMMENT_EDIT_FORBIDDEN(3601, "이 댓글을 수정할 권한이 없습니다."),
    COMMENT_DELETE_FORBIDDEN(3602, "이 댓글을 삭제할 권한이 없습니다."),
    COMMENT_REPORT_FORBIDDEN(3602, "이 댓글을 신고할 권한이 없습니다."),

    /**
     * Apple 관련 예외
     */
    REQUEST_APPLE_AUTH_KEYS(10200, "Apple auth keys 조회 중 오류가 발생했습니다."),
    INVALID_APPLE_ID_TOKEN(10201, "유효하지 않은 id token입니다."),
}
