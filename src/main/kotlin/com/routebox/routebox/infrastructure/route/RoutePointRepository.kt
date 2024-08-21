package com.routebox.routebox.infrastructure.route

import com.routebox.routebox.domain.route.RoutePoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoutePointRepository : JpaRepository<RoutePoint, Long>
