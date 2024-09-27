package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.CheckProgressRouteUseCase
import com.routebox.routebox.application.route.CreateRouteActivityUseCase
import com.routebox.routebox.application.route.CreateRoutePointUseCase
import com.routebox.routebox.application.route.CreateRouteUseCase
import com.routebox.routebox.application.route.DeleteRouteActivityUseCase
import com.routebox.routebox.application.route.DeleteRouteUseCase
import com.routebox.routebox.application.route.FinishRecordRouteUseCase
import com.routebox.routebox.application.route.GetMyRouteListUseCase
import com.routebox.routebox.application.route.GetRouteActivityDetailUseCase
import com.routebox.routebox.application.route.UpdateRouteActivityUseCase
import com.routebox.routebox.application.route.UpdateRoutePublicUseCase
import com.routebox.routebox.application.route.UpdateRouteUseCase
import com.routebox.routebox.application.route.dto.DeleteRouteActivityCommand
import com.routebox.routebox.application.route.dto.DeleteRouteCommand
import com.routebox.routebox.controller.route.dto.CheckProgressRouteResponse
import com.routebox.routebox.controller.route.dto.CreateRouteActivityRequest
import com.routebox.routebox.controller.route.dto.CreateRouteActivityResponse
import com.routebox.routebox.controller.route.dto.CreateRoutePointRequest
import com.routebox.routebox.controller.route.dto.CreateRoutePointResponse
import com.routebox.routebox.controller.route.dto.CreateRouteRequest
import com.routebox.routebox.controller.route.dto.CreateRouteResponse
import com.routebox.routebox.controller.route.dto.DeleteRouteActivityResponse
import com.routebox.routebox.controller.route.dto.DeleteRouteResponse
import com.routebox.routebox.controller.route.dto.FinishRecordRouteRequest
import com.routebox.routebox.controller.route.dto.FinishRecordRouteResponse
import com.routebox.routebox.controller.route.dto.GetMyRouteInsightResponse
import com.routebox.routebox.controller.route.dto.GetMyRouteResponse
import com.routebox.routebox.controller.route.dto.RouteActivityResponse
import com.routebox.routebox.controller.route.dto.RouteSimpleResponse
import com.routebox.routebox.controller.route.dto.UpdateRouteActivityRequest
import com.routebox.routebox.controller.route.dto.UpdateRouteActivityResponse
import com.routebox.routebox.controller.route.dto.UpdateRoutePublicRequest
import com.routebox.routebox.controller.route.dto.UpdateRoutePublicResponse
import com.routebox.routebox.controller.route.dto.UpdateRouteRequest
import com.routebox.routebox.controller.route.dto.UpdateRouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

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
    private val updateRouteUseCase: UpdateRouteUseCase,
    private val deleteRouteUseCase: DeleteRouteUseCase,
    private val updateRoutePublicUseCase: UpdateRoutePublicUseCase,
    private val checkProgressRouteUseCase: CheckProgressRouteUseCase,
    private val getMyRouteListUseCase: GetMyRouteListUseCase,
    private val finishRecordRouteUseCase: FinishRecordRouteUseCase,
    private val getRouteActivityDetailUseCase: GetRouteActivityDetailUseCase,
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

    @Operation(
        summary = "루트 수정",
        description = "<p>루트 마무리 - 루트 스타일 입력, 루트 수정에서 사용</p>" +
            "<p>null이 아닌 값들만 수정됨</p>" +
            "<p>루트 제목, 설명 입력 후 최종 마무리하는 건 별도의 루트 마무리 API 사용</p>",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PutMapping("/v1/routes/{routeId}")
    fun updateRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody @Valid request: UpdateRouteRequest,
    ): UpdateRouteResponse {
        val routeResponse = updateRouteUseCase(request.toCommand(userId = userPrincipal.userId, routeId = routeId))
        return UpdateRouteResponse.from(routeResponse)
    }

    @Operation(
        summary = "루트 삭제",
        security = [SecurityRequirement(name = "access-token")],
    )
    @DeleteMapping("/v1/routes/{routeId}")
    fun deleteRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
    ): DeleteRouteResponse {
        val routeResponse = deleteRouteUseCase(DeleteRouteCommand(userId = userPrincipal.userId, routeId = routeId))
        return DeleteRouteResponse.from(routeResponse)
    }

    @Operation(
        summary = "루트 공개여부 수정",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PatchMapping("/v1/routes/{routeId}/public")
    fun updateRoutePublic(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody @Valid request: UpdateRoutePublicRequest,
    ): UpdateRoutePublicResponse {
        val routeResponse =
            updateRoutePublicUseCase(request.toCommand(userId = userPrincipal.userId, routeId = routeId))
        return UpdateRoutePublicResponse.from(routeResponse)
    }

    @Operation(
        summary = "내루트 목록 조회",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/my")
    fun getMyRouteList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): GetMyRouteResponse {
        val routeResponses = getMyRouteListUseCase.invoke(userPrincipal.userId)
        return GetMyRouteResponse.from(routeResponses.map { RouteSimpleResponse.from(it) })
    }

    @Operation(
        summary = "인사이트 조회 (더미데이터)",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/insight")
    fun getMyRouteInsight(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): GetMyRouteInsightResponse {
        // TODO: 구현
        val routeCount = Random.nextInt(0, 21)
        val purchaseCount = Random.nextInt(0, 101)
        val commentCount = Random.nextInt(0, 101)

        return GetMyRouteInsightResponse(routeCount, purchaseCount, commentCount)
    }

    @Operation(
        summary = "기록 진행중인 루트 여부 조회",
        description = "<p>기록 진행중인 루트가 존재하는 경우 <code>routeId: Int</code>반환, 없는 경우 <code>routeId : null</code> 반환</p>",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/progress")
    fun checkProgressRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): CheckProgressRouteResponse {
        val routeId = checkProgressRouteUseCase(
            userPrincipal.userId,
        )
        return CheckProgressRouteResponse(routeId)
    }

    @Operation(
        summary = "루트 마무리",
        description = "루트 제목, 설명 수정 및 루트 기록 마무리 처리됨",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PatchMapping("/v1/routes/{routeId}/record-finish")
    fun finishRecordRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody @Valid request: FinishRecordRouteRequest,
    ): FinishRecordRouteResponse {
        val routeResponse = finishRecordRouteUseCase(request.toCommand(routeId = routeId))
        return FinishRecordRouteResponse.from(
            routeId = routeResponse.routeId,
            name = routeResponse.name,
            description = routeResponse.description,
            recordFinishedAt = routeResponse.recordFinishedAt,
        )
    }

    @Operation(
        summary = "활동 상세 조회",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/{routeId}/activity/{activityId}")
    fun getRouteActivityDetail(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @PathVariable activityId: Long,
    ): RouteActivityResponse {
        val activity = getRouteActivityDetailUseCase(activityId)
        return RouteActivityResponse.from(activity)
    }
}
