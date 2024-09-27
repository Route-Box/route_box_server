package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.CreateRouteActivityCommand
import com.routebox.routebox.application.route.dto.CreateRouteActivityResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateRouteActivityUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 활동 추가
     *
     * @param command userId, routeId, locationName, address, latitude, longitude, visitDate, startTime, endTime, category, description, images
     * @return 루트 point id
     * @throws
     */
    @Transactional
    operator fun invoke(command: CreateRouteActivityCommand): CreateRouteActivityResult {
        val routeActivity = routeService.createRouteActivity(
            routeId = command.routeId,
            locationName = command.locationName,
            address = command.address,
            latitude = command.latitude,
            longitude = command.longitude,
            visitDate = command.visitDate,
            startTime = command.startTime,
            endTime = command.endTime,
            category = command.category,
            description = command.description,
            images = command.images,
        )
        return CreateRouteActivityResult.from(routeActivity)
    }
}
