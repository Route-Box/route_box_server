package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.CreateRouteActivityUseCase
import com.routebox.routebox.application.route.CreateRoutePointUseCase
import com.routebox.routebox.application.route.CreateRouteUseCase
import com.routebox.routebox.application.route.DeleteRouteActivityUseCase
import com.routebox.routebox.application.route.UpdateRouteActivityUseCase
import com.routebox.routebox.application.route.dto.DeleteRouteActivityCommand
import com.routebox.routebox.controller.route.dto.CreateRouteActivityRequest
import com.routebox.routebox.controller.route.dto.CreateRouteActivityResponse
import com.routebox.routebox.controller.route.dto.CreateRoutePointRequest
import com.routebox.routebox.controller.route.dto.CreateRoutePointResponse
import com.routebox.routebox.controller.route.dto.CreateRouteRequest
import com.routebox.routebox.controller.route.dto.CreateRouteResponse
import com.routebox.routebox.controller.route.dto.DeleteRouteActivityResponse
import com.routebox.routebox.controller.route.dto.UpdateRouteActivityRequest
import com.routebox.routebox.controller.route.dto.UpdateRouteActivityResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "내루트 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteCommandController(
    private val createRouteUseCase: CreateRouteUseCase,
    private val createRoutePointUseCase: CreateRoutePointUseCase,
    private val createRouteActivityUseCase: CreateRouteActivityUseCase,
    private val updateRouteActivityUseCase: UpdateRouteActivityUseCase,
    private val deleteRouteActivityUseCase: DeleteRouteActivityUseCase,
) {
    @Operation(
        summary = "루트 생성 (루트 기록 시작)",
        description = "루트 기록 시작일시, 종료일시 등록",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/routes/start")
    fun createRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: CreateRouteRequest,
    ): CreateRouteResponse {
        val routeResponse = createRouteUseCase(request.toCommand(userId = userPrincipal.userId))
        return CreateRouteResponse.from(routeResponse.routeId)
    }

    @Operation(
        summary = "루트 경로(점) 기록",
        description = "1분마다 현재 위치 보내서 루트 경로의 점 찍는데 사용",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/routes/{routeId}/point")
    fun createRoutePoint(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody @Valid request: CreateRoutePointRequest,
    ): CreateRoutePointResponse {
        val routeResponse = createRoutePointUseCase(request.toCommand(userId = userPrincipal.userId, routeId = routeId))
        return CreateRoutePointResponse.from(routeResponse.pointId)
    }

    @Operation(
        summary = "루트 활동 추가",
        description = "<p>요청 시 content-type은 <code>multipart/form-data</code>로 설정하여 요청해야 합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/routes/{routeId}/activity", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createRouteActivity(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @ModelAttribute @Valid request: CreateRouteActivityRequest,
    ): CreateRouteActivityResponse {
        val routeActivityResponse =
            createRouteActivityUseCase(request.toCommand(userId = userPrincipal.userId, routeId = routeId))
        return CreateRouteActivityResponse.from(routeActivityResponse)
    }

    @Operation(
        summary = "루트 활동 수정",
        description = "<p>요청 시 content-type은 <code>multipart/form-data</code>로 설정하여 요청해야 합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PutMapping("/v1/routes/{routeId}/activity/{activityId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateRouteActivity(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @PathVariable activityId: Long,
        @ModelAttribute @Valid request: UpdateRouteActivityRequest,
    ): UpdateRouteActivityResponse {
        val routeActivityResponse =
            updateRouteActivityUseCase(request.toCommand(activityId = activityId))
        return UpdateRouteActivityResponse.from(routeActivityResponse)
    }

    @Operation(
        summary = "루트 활동 삭제",
        security = [SecurityRequirement(name = "access-token")],
    )
    @DeleteMapping("/v1/routes/{routeId}/activity/{activityId}")
    fun deleteRouteActivity(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @PathVariable activityId: Long,
    ): DeleteRouteActivityResponse {
        val deleteRouteActivityResult = deleteRouteActivityUseCase(DeleteRouteActivityCommand(activityId))
        return DeleteRouteActivityResponse.from(deleteRouteActivityResult)
    }
}
