package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.converter.UserRoleTypesConverter
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.domain.user.constant.UserRoleType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table(name = "users")
@Entity
class User(
    loginType: LoginType,
    socialLoginUid: String,
    nickname: String,
    gender: Gender,
    birthDay: LocalDate,
    id: Long = 0,
    roles: Set<UserRoleType> = setOf(UserRoleType.USER),
    profileImageUrl: String = USER_DEFAULT_PROFILE_IMAGE_URL,
    point: Int = 0,
    introduction: String = "",
    notificationSettings: UserNotificationSettings = UserNotificationSettings(),
    deletedAt: LocalDateTime? = null,
) : TimeTrackedBaseEntity() {

    companion object {
        const val USER_DEFAULT_PROFILE_IMAGE_URL =
            "https://www.pngkey.com/png/detail/99-999572_clipart-stock-woman-user-avatar-svg-png-icon.png"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = id

    @Convert(converter = UserRoleTypesConverter::class)
    var roles: Set<UserRoleType> = roles
        private set

    @Enumerated(EnumType.STRING)
    val loginType: LoginType = loginType

    val socialLoginUid: String = socialLoginUid

    var profileImageUrl: String = profileImageUrl
        private set

    var nickname: String = nickname
        private set

    var point: Int = point
        private set

    @Enumerated(EnumType.STRING)
    var gender: Gender = gender
        private set

    var birthDay: LocalDate = birthDay
        private set

    var introduction: String = introduction
        private set

    @Embedded
    var notificationSettings: UserNotificationSettings = notificationSettings
        private set

    var deletedAt: LocalDateTime? = deletedAt
        private set

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateGender(gender: Gender) {
        this.gender = gender
    }

    fun updateBirthDay(birthday: LocalDate) {
        this.birthDay = birthday
    }

    fun updateIntroduction(introduction: String) {
        this.introduction = introduction
    }

    fun updateProfileImageUrl(profileImageUrl: String) {
        this.profileImageUrl = profileImageUrl
    }
}
