package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "route_points")
@Entity
class RoutePoint(
    id: Long = 0,
    route: Route,
    latitude: String,
    longitude: String,
    recordAt: LocalDateTime,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    val route: Route = route

    @Column(name = "latitude", nullable = false)
    var latitude: String = latitude

    @Column(name = "longitude", nullable = false)
    var longitude: String = longitude

    @Column(name = "record_at", nullable = false)
    var recordAt: LocalDateTime = recordAt
}
