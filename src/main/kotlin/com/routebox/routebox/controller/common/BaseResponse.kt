package com.routebox.routebox.controller.common

import io.swagger.v3.oas.annotations.media.Schema

data class BaseResponse<T>(
    @Schema(description = "성공 여부", example = "true")
    val isSuccess: Boolean,

    @Schema(description = "응답 데이터")
    val result: T,
) {
    companion object {
        fun <T> success(result: T): BaseResponse<T> = BaseResponse(true, result)
        fun <T> failure(result: T): BaseResponse<T> = BaseResponse(false, result)
    }
}
