package com.routebox.routebox.infrastructure.route

import com.routebox.routebox.domain.route.RouteActivity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteActivityRepository : JpaRepository<RouteActivity, Long>
