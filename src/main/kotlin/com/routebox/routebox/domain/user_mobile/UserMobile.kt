package com.routebox.routebox.domain.user_mobile

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserMobile(
    userId: Long,
    pushToken: String?,
    osType: String,
    id: Long = 0,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mobile_id")
    val id: Long = id

    @Column(name = "user_id", nullable = false)
    val userId: Long = userId

    @Column
    var pushToken: String? = pushToken

    @Column
    var osType: String = osType

    fun update(pushToken: String?, osType: String) {
        this.pushToken = pushToken
        this.osType = osType
    }
}
