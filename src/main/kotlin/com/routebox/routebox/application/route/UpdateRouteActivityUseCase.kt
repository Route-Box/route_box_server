package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.UpdateRouteActivityCommand
import com.routebox.routebox.application.route.dto.UpdateRouteActivityResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateRouteActivityUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 활동 수정
     *
     * @param command activityId, locationName, address, latitude, longitude, visitDate, startTime, endTime, category, description, images
     * @return 루트 point id
     * @throws
     */
    @Transactional
    operator fun invoke(command: UpdateRouteActivityCommand): UpdateRouteActivityResult {
        // TODO: 루트 id 검증
        // TODO: 삭제된 이미지 id 검증
        val routeActivity = routeService.updateRouteActivity(
            activityId = command.activityId,
            locationName = command.locationName,
            address = command.address,
            latitude = command.latitude,
            longitude = command.longitude,
            visitDate = command.visitDate,
            startTime = command.startTime,
            endTime = command.endTime,
            category = command.category,
            description = command.description,
            addedImages = command.addedImages,
            deletedImageIds = command.deletedImageIds,
        )
        return UpdateRouteActivityResult.from(routeActivity)
    }
}
