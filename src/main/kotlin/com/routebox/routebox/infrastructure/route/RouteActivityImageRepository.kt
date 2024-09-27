package com.routebox.routebox.infrastructure.route

import com.routebox.routebox.domain.route.RouteActivityImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteActivityImageRepository : JpaRepository<RouteActivityImage, Long>
