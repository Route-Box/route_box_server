package com.routebox.routebox.domain.recommended_route

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "recommended_routes")
@Entity
class RecommendedRoute(
    id: Long = 0,
    routeId: Long,
    showFrom: LocalDateTime,
    commonComment: String? = null,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_route_id")
    val id: Long = id

    val routeId: Long = routeId

    val showFrom: LocalDateTime = showFrom

    val commonComment: String? = commonComment
}
