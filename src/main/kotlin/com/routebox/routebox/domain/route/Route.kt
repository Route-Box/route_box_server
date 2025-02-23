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
    styles: String,
    transportation: String?,
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

    var styles: String? = styles
        private set

    var transportation: String? = transportation
        private set

    var isPublic: Boolean = isPublic
        private set

    var recordFinishedAt: LocalDateTime? = null
        private set

    @OneToMany(mappedBy = "route")
    var routePoints: List<RoutePoint> = mutableListOf()
        private set

    @OneToMany(mappedBy = "route")
    var routeActivities: List<RouteActivity> = mutableListOf()
        private set

    fun updatePublic(isPublic: Boolean) {
        this.isPublic = isPublic
    }

    fun updateName(name: String) {
        // TODO: 루트마무리 API 클라이언트에서 엮은 후 제거 (임시 코드)
        if (this.name == null) {
            // 루트 마무리 처리
            this.recordFinishedAt = LocalDateTime.now()
        }

        this.name = name
    }

    fun updateDescription(description: String) {
        this.description = description
    }

    fun updateWhoWith(whoWith: String) {
        this.whoWith = whoWith
    }

    fun updateNumberOfPeople(numberOfPeople: Int) {
        this.numberOfPeople = numberOfPeople
    }

    fun updateNumberOfDays(numberOfDays: String) {
        this.numberOfDays = numberOfDays
    }

    fun updateStyle(style: Array<String>) {
        this.styles = style.joinToString(",")
    }

    fun updateTransportation(transportation: String) {
        this.transportation = transportation
    }

    fun finishRecord(name: String, description: String?) {
        this.name = name
        this.description = description
        this.recordFinishedAt = LocalDateTime.now()
    }

    fun getRoutePath(): List<Map<String, String>> = routePoints.map {
        mapOf(
            "latitude" to it.latitude,
            "longitude" to it.longitude,
            "recordAt" to it.recordAt.toString(),
        )
    }.sortedBy { it["recordAt"] }
}
