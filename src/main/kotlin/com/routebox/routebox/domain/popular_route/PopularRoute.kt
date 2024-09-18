package com.routebox.routebox.domain.popular_route

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "popular_routes")
@Entity
class PopularRoute(
    id: Long = 0,
    routeId: Long,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popular_route_id")
    val id: Long = id

    val routeId: Long = routeId
}
