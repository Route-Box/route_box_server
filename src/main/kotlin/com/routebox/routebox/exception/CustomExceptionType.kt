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
    EXAMPLE(2000, "알 수 없는 에러가 발생했습니다."),
}
