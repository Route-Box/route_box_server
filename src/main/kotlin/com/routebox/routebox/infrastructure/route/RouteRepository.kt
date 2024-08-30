package com.routebox.routebox.infrastructure.route

import com.routebox.routebox.domain.route.Route
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Suppress("ktlint:standard:function-naming")
@Repository
interface RouteRepository : JpaRepository<Route, Long> {
    @Query(
        """
        SELECT r FROM Route r
        WHERE r.isPublic = true
        AND r.id NOT IN (SELECT rr.reportedRouteId FROM RouteReport rr WHERE rr.reporterId = :userId)
        AND r.user.id NOT IN (SELECT ur.reportedUserId FROM UserReport ur WHERE ur.reporterId = :userId)
        ORDER BY r.recordFinishedAt DESC
    """,
    )
    fun findAllFiltered(userId: Long, pageable: Pageable): Page<Route>

    fun countByUser_Id(userId: Long): Int
    fun findByRecordFinishedAtIsNullAndUser_Id(userId: Long): List<Route>
    fun findByUser_IdAndRecordFinishedAtIsNotNullOrderByRecordFinishedAtDesc(userId: Long): List<Route>
    fun findByUser_IdAndIsPublicOrderByRecordFinishedAtDesc(userId: Long, isPublic: Boolean): List<Route>
}
