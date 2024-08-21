package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime

@Table(name = "route_activities")
@Entity
class RouteActivity(
    id: Long = 0,
    route: Route? = null,
    locationName: String,
    address: String,
    latitude: String?,
    longitude: String?,
    visitDate: LocalDate,
    startTime: LocalTime,
    endTime: LocalTime,
    category: String,
    description: String?,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_activity_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    val route: Route? = route

    @Column(name = "location_name", nullable = false)
    var locationName: String = locationName

    @Column(name = "address")
    var address: String = address

    @Column(name = "latitude", nullable = false)
    var latitude: String? = latitude

    @Column(name = "longitude", nullable = false)
    var longitude: String? = longitude

    @Column(name = "visit_date", nullable = false)
    var visitDate: LocalDate = visitDate

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime = startTime

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime = endTime

    @Column(name = "category")
    var category: String = category

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = description

    @OneToMany(mappedBy = "activity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val activityImages: MutableList<RouteActivityImage> = mutableListOf()
}
