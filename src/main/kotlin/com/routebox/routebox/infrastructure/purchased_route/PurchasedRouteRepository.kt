package com.routebox.routebox.infrastructure.purchased_route

import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import org.springframework.data.jpa.repository.JpaRepository

interface PurchasedRouteRepository : JpaRepository<PurchasedRoute, Long>
