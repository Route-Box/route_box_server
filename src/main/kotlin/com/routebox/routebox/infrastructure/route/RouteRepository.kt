package com.routebox.routebox.infrastructure.route

import com.routebox.routebox.domain.route.Route
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Suppress("ktlint:standard:function-naming")
@Repository
interface RouteRepository : JpaRepository<Route, Long> {
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Route>

    fun countByUser_Id(userId: Long): Int
    fun findByEndTimeIsAfterAndUser_Id(endTime: LocalDateTime, userId: Long): Route?
}
