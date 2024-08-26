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
    user: User,
    name: String?,
    description: String?,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    whoWith: String?,
    numberOfPeople: Int?,
    numberOfDays: String?,
    style: Array<String>,
    transportations: String?,
    isPublic: Boolean = false,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = user

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

    var numberOfPeople: Int? = numberOfPeople
        private set

    var numberOfDays: String? = numberOfDays
        private set

    @Convert(converter = StringArrayConverter::class)
    @Column(columnDefinition = "json")
    var style: Array<String> = style
        private set

    var transportations: String? = transportations
        private set

    var isPublic: Boolean = isPublic
        private set

    @OneToMany(mappedBy = "route")
    var routePoints: List<RoutePoint> = mutableListOf()
        private set

    @OneToMany(mappedBy = "route")
    var routeActivities: List<RouteActivity> = mutableListOf()
        private set

    fun update(
        name: String?,
        description: String?,
        whoWith: String?,
        numberOfPeople: Int?,
        numberOfDays: String?,
        style: Array<String>,
        transportation: String?,
    ) {
        this.name = name
        this.description = description
        this.whoWith = whoWith
        this.numberOfPeople = numberOfPeople
        this.numberOfDays = numberOfDays
        this.style = style
        this.transportations = transportation
    }

    fun updatePublic(isPublic: Boolean) {
        this.isPublic = isPublic
    }
}
