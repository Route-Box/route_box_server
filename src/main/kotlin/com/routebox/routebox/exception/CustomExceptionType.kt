package com.routebox.routebox.exception

/**
 * Error code 목록
 *
 * - 2XXX: 일반 예외. 아래 항목에 해당하지 않는 대부분의 예외가 여기에 해당한다.
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
}
