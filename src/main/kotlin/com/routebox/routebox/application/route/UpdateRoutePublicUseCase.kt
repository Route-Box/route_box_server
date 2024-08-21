package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.UpdateRoutePublicCommand
import com.routebox.routebox.application.route.dto.UpdateRoutePublicResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateRoutePublicUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 공개여부 수정
     *
     * @param command userId, routeId, isPublic
     * @return routeId, isPublic
     * @throws
     */
    @Transactional
    operator fun invoke(command: UpdateRoutePublicCommand): UpdateRoutePublicResult {
        // TODO: user id 검증
        val route = routeService.updateRoutePublic(
            routeId = command.routeId,
            isPublic = command.isPublic,
        )
        return UpdateRoutePublicResult.from(route)
    }
}
