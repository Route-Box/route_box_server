package com.routebox.routebox.infrastructure.recommended_route

import com.routebox.routebox.application.recommended_route.dto.RecommendedRouteDto
import com.routebox.routebox.domain.recommended_route.RecommendedRoute
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecommendedRouteRepository : JpaRepository<RecommendedRoute, Long> {
    @Query(
        """
            SELECT NEW com.routebox.routebox.application.recommended_route.dto.RecommendedRouteDto(
                r.id, r.name, r.description, rr.commonComment,
                CASE WHEN rai.fileUrl IS NOT NULL THEN rai.fileUrl ELSE "" END
            )
            FROM RecommendedRoute rr
            JOIN Route r ON rr.routeId = r.id
            LEFT JOIN RouteActivity ra ON ra.route = r
            LEFT JOIN RouteActivityImage rai ON rai.activity = ra
            WHERE r.isPublic = true AND rr.showFrom <= CURRENT_TIMESTAMP
            GROUP BY r.id
        """,
    )
    fun findAllRecommendedRoutes(): List<RecommendedRouteDto>
}
