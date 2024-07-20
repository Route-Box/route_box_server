package com.routebox.routebox.exception

/**
 * Error code 목록.
 * Error code 값은 각 도메인(또는 기능)별로 **200** 단위씩 끊어서 정의한다.
 *
 * - 2XXX: 일반 예외. 아래 항목에 해당하지 않는 대부분의 예외가 여기에 해당한다.
 * - 3000 ~ 3199: 유저 관련 예외
 * - 10000 ~ 10199: kakao(kakao open api 등) 관련 관련 예외
 */
enum class CustomExceptionType(
    val code: Int,
    val message: String,
) {
    /**
     * 로그인, 인증 관련 예외
     */
    ACCESS_DENIED(2000, "접근이 거부되었습니다."),
    UNAUTHORIZED(2001, "유효하지 않은 인증 정보로 인해 인증 과정에서 문제가 발생하였습니다."),
    INVALID_TOKEN(2002, "유효하지 않은 토큰입니다. 토큰 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다."),

    /**
     * 유저 관련 예외
     */
    USER_NOT_FOUND(3000, "일치하는 유저를 찾을 수 없습니다."),
    USER_SOCIAL_LOGIN_UID_DUPLICATION(3001, "이미 가입한 계정입니다."),

    /**
     * Kakao(kakao open api 등) 관련 예외
     */
    REQUEST_KAKAO_USER_INFO(10000, "카카오 사용자 정보 조회 중 오류가 발생했습니다."),

    /**
     * Apple 관련 예외
     */
    REQUEST_APPLE_AUTH_KEYS(10200, "Apple auth keys 조회 중 오류가 발생했습니다."),
    INVALID_APPLE_ID_TOKEN(10201, "유효하지 않은 id token입니다."),
}
