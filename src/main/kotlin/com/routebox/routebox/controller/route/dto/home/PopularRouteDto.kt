package com.routebox.routebox.controller.route.dto.home

import io.swagger.v3.oas.annotations.media.Schema

data class PopularRouteDto(
    @Schema(description = "루트 ID")
    val id: Long,

    @Schema(description = "루트 이름")
    val name: String,
) {
    companion object {
        fun from(id: Long, name: String): PopularRouteDto = PopularRouteDto(
            id = id,
            name = name,
        )
    }
}
