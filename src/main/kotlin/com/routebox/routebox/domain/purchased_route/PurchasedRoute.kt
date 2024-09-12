package com.routebox.routebox.domain.purchased_route

import com.routebox.routebox.domain.common.BaseEntity
import com.routebox.routebox.domain.converter.StringArrayConverter
import com.routebox.routebox.domain.purchased_route.converter.PurchasedRouteActivityConverter
import com.routebox.routebox.domain.purchased_route.converter.PurchasedRoutePointConverter
import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "purchased_route")
@Entity
class PurchasedRoute(
    id: Long = 0,
    buyer: User,
    writer: User,
    routeId: Long,
    name: String?,
    description: String?,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    whoWith: String?,
    numberOfPeoples: Int?,
    numberOfDays: String?,
    styles: Array<String>,
    transportation: String?,
    routePoints: List<PurchasedRoutePoint>,
    routeActivities: List<PurchasedRouteActivity>,
) : BaseEntity() {
    companion object {
        fun fromRoute(route: Route, buyer: User): PurchasedRoute = PurchasedRoute(
            buyer = buyer,
            writer = route.user,
            routeId = route.id,
            name = route.name,
            description = route.description,
            startTime = route.startTime,
            endTime = route.endTime,
            whoWith = route.whoWith,
            numberOfPeoples = route.numberOfPeople,
            numberOfDays = route.numberOfDays,
            styles = route.style,
            transportation = route.name,
            routePoints = route.routePoints.map { PurchasedRoutePoint.fromRoutePoint(it) },
            routeActivities = route.routeActivities.map { PurchasedRouteActivity.fromRouteActivity(it) },
        )
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_route_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    val buyer: User = buyer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    val writer: User = writer

    val routeId: Long = routeId

    var name: String? = name
        private set

    var description: String? = description
        private set

    var startTime: LocalDateTime = startTime
        private set

    var endTime: LocalDateTime = endTime
        private set

    var whoWith: String? = whoWith
        private set

    var numberOfPeoples: Int? = numberOfPeoples
        private set

    var numberOfDays: String? = numberOfDays
        private set

    @Convert(converter = StringArrayConverter::class)
    @Column(columnDefinition = "JSON")
    var styles: Array<String> = styles
        private set

    var transportation: String? = transportation
        private set

    @Convert(converter = PurchasedRoutePointConverter::class)
    @Column(columnDefinition = "JSON")
    var routePoints: List<PurchasedRoutePoint> = routePoints
        private set

    @Convert(converter = PurchasedRouteActivityConverter::class)
    @Column(columnDefinition = "JSON")
    var routeActivities: List<PurchasedRouteActivity> = routeActivities
        private set
}
