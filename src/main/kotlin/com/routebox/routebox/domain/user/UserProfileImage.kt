package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.common.FileEntity
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class UserProfileImage(
    userId: Long,
    storedFileName: String,
    fileUrl: String,
    id: Long = 0,
) : FileEntity(storedFileName, fileUrl) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_image_id")
    val id: Long = id

    val userId: Long = userId
}
