package com.routebox.routebox.infrastructure.user

import com.routebox.routebox.domain.user.UserProfileImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

// UserProfileImage의 경우 soft delete가 적용되어 있으므로 query 작성 시 이를 고려해야 한다.
interface UserProfileImageRepository : JpaRepository<UserProfileImage, Long> {
    @Query("SELECT upi FROM UserProfileImage upi WHERE upi.deletedAt != null")
    override fun findById(id: Long): Optional<UserProfileImage>

    @Query("SELECT upi FROM UserProfileImage upi WHERE upi.deletedAt != null AND upi.userId = :userId")
    fun findByUserId(userId: Long): UserProfileImage?
}
