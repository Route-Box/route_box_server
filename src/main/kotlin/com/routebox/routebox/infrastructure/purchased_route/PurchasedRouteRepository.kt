package com.routebox.routebox.infrastructure.purchased_route

import com.routebox.routebox.application.popular_route.dto.PopularRouteCountDto
import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

@Suppress("ktlint:standard:function-naming")
interface PurchasedRouteRepository : JpaRepository<PurchasedRoute, Long> {
    fun findByBuyer_IdOrderByCreatedAtDesc(buyerId: Long, pageable: Pageable): Page<PurchasedRoute>
    fun countByBuyer_Id(buyerId: Long): Int

    @Query(
        """
    SELECT NEW com.routebox.routebox.application.popular_route.dto.PopularRouteCountDto(
        r.id, COALESCE(COUNT(pr), 0)
    )
    FROM Route r
    LEFT JOIN PopularRoute pr ON r.id = pr.routeId
    GROUP BY r.id
    ORDER BY COUNT(pr) DESC
    """,
    )
    fun findTop5PopularRoutes(pageable: Pageable): List<PopularRouteCountDto>
}
