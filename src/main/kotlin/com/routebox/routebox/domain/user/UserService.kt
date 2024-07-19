package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.constant.Gender
import com.routebox.routebox.domain.constant.LoginType
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.user.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun getUserById(id: Long): User =
        userRepository.findById(id).orElseThrow { UserNotFoundException() }
}
