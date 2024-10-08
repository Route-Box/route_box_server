package com.routebox.routebox.application.user.dto

import com.routebox.routebox.domain.user.constant.Gender
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class UpdateUserInfoCommand(
    val id: Long,
    val nickname: String?,
    val gender: Gender?,
    val birthDay: LocalDate?,
    val introduction: String?,
    val profileImage: MultipartFile?,
)
