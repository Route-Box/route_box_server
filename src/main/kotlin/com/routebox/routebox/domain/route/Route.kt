package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.converter.StringArrayConverter
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
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "routes")
@Entity
class Route(
    id: Long = 0,
    name: String?,
    description: String?,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    whoWith: String?,
    numberOfPeople: Int?,
    numberOfDays: String?,
    style: Array<String>,
    transportation: Array<String>,
    isPublic: Boolean = false,
    user: User? = null,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = user

    var name: String? = name

    var description: String? = description

    var startTime: LocalDateTime = startTime

    var endTime: LocalDateTime = endTime

    var whoWith: String? = whoWith

    var numberOfPeople: Int? = numberOfPeople

    var numberOfDays: String? = numberOfDays

    @Convert(converter = StringArrayConverter::class)
    @Column(columnDefinition = "json")
    var style: Array<String> = style

    @Convert(converter = StringArrayConverter::class)
    @Column(columnDefinition = "json")
    var transportation: Array<String> = transportation

    var isPublic: Boolean = isPublic

    @OneToMany(mappedBy = "route")
    var routePoints: List<RoutePoint> = mutableListOf()
}
