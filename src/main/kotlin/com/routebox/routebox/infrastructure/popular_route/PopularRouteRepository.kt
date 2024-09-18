package com.routebox.routebox.infrastructure.popular_route

import com.routebox.routebox.application.popular_route.dto.PopularRouteDto
import com.routebox.routebox.domain.popular_route.PopularRoute
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PopularRouteRepository : JpaRepository<PopularRoute, Long> {
    @Query(
        """
            SELECT NEW com.routebox.routebox.application.popular_route.dto.PopularRouteDto(r.id, r.name)
            FROM PopularRoute pr
            JOIN Route r ON pr.routeId = r.id
            WHERE r.isPublic = true
        """,
    )
    fun findAllPopularRoutes(): List<PopularRouteDto>
}
