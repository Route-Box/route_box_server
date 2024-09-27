package com.routebox.routebox.infrastructure.purchased_route

import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint:standard:function-naming")
interface PurchasedRouteRepository : JpaRepository<PurchasedRoute, Long> {
    fun findByBuyer_IdOrderByCreatedAtDesc(buyerId: Long, pageable: Pageable): Page<PurchasedRoute>
}
